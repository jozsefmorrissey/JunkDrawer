<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/18/16
 * Time: 12:16 AM
 */
class svn_list
{
    /**
     * @param $svn_lists
     */
    public static function init_svn_list(&$entries, $file_path)
    {
        $svn_lists = parse_svn::parse_list($file_path);
        usort($svn_lists, "self::list_cmp");

        foreach ($svn_lists as $listEntry) {
            $head = $listEntry->getPath();
            if (!array_key_exists((string)$head, $entries)) {
                self::rootTree((string)$head, $entries);
            }
            $kind = $listEntry->getkind();
            $path = $listEntry->getName();
            $size = $listEntry->getSize();
            $revision_number = $listEntry->getRevision();
            $author = $listEntry->getAuthor();
            $date = $listEntry->getDate();

            self::createUpdateListElement($entries, $path, $revision_number, $author, $date, $kind, $size);
        }
    }

    private static function get_organization_team($path){
        $elements = self::get_path_array($path);
        if(sizeof($elements) > 3){
            return array(NAME => $elements[sizeof($elements) - 1], ORGANIZATION => $elements[1], TEAM => $elements[3]);
        }
        if(sizeof($elements) > 1){
            return array(NAME => $elements[sizeof($elements) - 1], ORGANIZATION => $elements[1], TEAM => "");
        }
        return array(NAME => $elements[sizeof($elements) - 1], ORGANIZATION => "", TEAM => "");
    }

    public static function get_path_array($path){
        $path_array = array();

        $index = 0;
        $sub_loc = strtok($path, "/");
        while($sub_loc){
            $path_array[$index++] = $sub_loc;
            $sub_loc = strtok("/");
        }
        return $path_array;
    }

    public static function getParent($path){
        $path = strrev($path);
        $run = true;
        while($run or $path[0] == '/'){
            $delm_index = strpos($path, "/", true);
            $path = substr($path, $delm_index + 1, strlen($path));
            $run = false;
        }

        $path = strrev($path);
        return $path;
    }

    public static function rootTree($path, &$entries){
        if(!strpos($path, "/")) {
            if (!array_key_exists($path, $entries)) {
                $name_org_team = self::get_organization_team($path);
                $entries[(string)$path] = new entry(null, $path, $name_org_team[NAME], null, null, null, null, null, $name_org_team[ORGANIZATION], $name_org_team[TEAM], null);
                return;
            }
        }
        $parent = self::getParent($path);
        self::rootTree($parent, $entries);
        $name_org_team = self::get_organization_team($path);
        if(!array_key_exists($path, $entries)){
            $entries[(string)$path] = new entry(null,$path,  $name_org_team[NAME], null, null, null, null, null, $name_org_team[ORGANIZATION], $name_org_team[TEAM], null);
        }
        $entries[$parent]->addToChildren($path);
    }

    /**
     * @param $path
     * @param $revision_number
     * @param $author
     * @param $date
     * @param $kind
     * @param $size
     */
    private static function createUpdateListElement(&$entries, $path, $revision_number, $author, $date, $kind, $size)
    {
        if (!array_key_exists($path, $entries)) {
            $name_org_team = self::get_organization_team($path);
            $parent = self::getParent($path);
            $revision = new revision($revision_number);
            $entries[$path] = new entry($parent, $path, $name_org_team[NAME], $author, $date, $kind, $revision, $size, $name_org_team[ORGANIZATION], $name_org_team[TEAM], "");
            $entries[$parent]->addToChildren((string)$path);
        } else {
            $revisions = $entries[$path]->getRevisions();
            if (!array_key_exists((string)$revision_number, $revisions)) {
                $new_revision = new revision($revision_number);
                $entries[$path]->addToRevisions($new_revision);
            }
        }
    }

    public static function list_cmp($entry1, $entry2)
    {   $path1 = $entry1->getName();
        $path2 = $entry2->getName();
        return strlen((string)$path1) > strlen((string)$path2);
    }
}