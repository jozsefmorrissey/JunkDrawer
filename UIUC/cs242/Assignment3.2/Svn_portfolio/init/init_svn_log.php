<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/18/16
 * Time: 12:41 AM
 */
class init_svn_log
{
    public static function init_svn_log(&$entries, $file_path)
    {
        $svn_logs = parse_svn::parse_log($file_path);

        foreach($svn_logs as $log){
            $date = $log->getDate();
            $author = $log->getAuthor();
            $message = $log->getMessage();
            $components = $log->getComponents();
            $revision = $log->getRevision();
            self::init_component($entries, $date, $message, $components, $revision);
        }

    }

    public static function log_cmp($entry1, $entry2)
    {   $path1 = $entry1->getComponents()->getPath();
        $path2 = $entry2->getComponents()->getPath();
        return strlen((string)$path1) > strlen((string)$path2);
    }

    /**
     * init_svn_log constructor.
     */
    public function __construct()
    {

    }

    private static function init_component(&$entries, $date, $message, $components, $revision){
        //usort($svn_logs, "self::log_cmp");
        $loop_count = 0;
        foreach($components as $component){
            $path = $component->getPath();
            if(array_key_exists((string)$path, $entries)){
                $operation = $component->getOperation();
                $revisions = $entries[(string)$path]->getRevisions();

                if(array_key_exists((string)$revision, $revisions)){
                    $revisions[(string)$revision]->setDate($date);
                    $revisions[(string)$revision]->setComment($message);
                    $revisions[(string)$revision]->setOperation($operation);
                }
                else{
                    $new_rev = new revision((string)$revision);
                    $entries[(string)$path]->addToRevisions($new_rev);
                    $new_rev->setDate($date);
                    $new_rev->setComment($message);
                    $new_rev->setOperation($operation);
                }

            }
        }
    }
}