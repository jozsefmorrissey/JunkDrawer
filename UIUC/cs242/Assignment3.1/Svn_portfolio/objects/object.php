<?php

//include '../svn/objects/svn_log_entry.php';
//include '../svn/objects/svn_list_entry.php';
//include '../svn/objects/svn_component.php';
include 'extended_classes/author.php';
include 'extended_classes/date.php';
include 'extended_classes/name.php';
include 'extended_classes/kind.php';
include 'extended_classes/operation.php';
include 'extended_classes/revision.php';
include 'extended_classes/size.php';
include 'organization.php';

//Class constants
const AUTHOR = 0;
const DATE = 1;
const KIND = 2;
const NAME = 3;
const OPERATION = 4;
const REVISION = 5;
const SIZE = 6;

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/7/16
 * Time: 4:07 AM
 */
abstract class object
{
    private static $author_list = array();
    private static $date_list = array();
    private static $kind_list = array();
    protected static $name_list = array();
    private static $operation_list = array();
    private static $revision_list = array();
    private static $size_list = array();
    private static $organization_list = array();

    private static $repo_path;
    protected static $root_node;
    private static $display_organization = null;
    private static $display_team = null;
    private static $display_project = null;

    protected $authors = array();
    protected $dates = array();
    protected $kinds = array();
    protected $names = array();
    protected $operations = array();
    protected $revisions = array();
    protected $sizes = array();

    protected $object;

    /**
     * size constructor.
     * @param $size
     */
    public function __construct($object)
    {
        $this->object = $object;
    }

    /**
     * @return mixed
     */
    public static function getRootNode()
    {
        return self::$root_node;
    }

    abstract protected function get_list();
    abstract protected function make_connections($author, $date, $kind, $name, $operation, $revision, $size);

    public static function add_log(svn_log $log){

        $date = self::get_object(object::$date_list, $log->getDate(), DATE);
        $author = self::get_object(object::$author_list, $log->getAuthor(), AUTHOR);
        $components = $log->getComponents();

        foreach($components as $component){
            $operation = self::get_object(object::$operation_list, $component->getOperation(), OPERATION);
            $name = name::find_node($component->getPath());
            $kind = self::get_object(object::$kind_list, $component->getKind(), KIND);

            self::disperse_info($author, $date, $kind, $name, $operation, null, null);
        }
    }

    public static function add_list(svn_list_entry $list){

        $date = self::get_object(object::$date_list, $list->getDate(), DATE);
        $author = self::get_object(object::$author_list, $list->getAuthor(), AUTHOR);
        $name = name::find_node($list->getName());
        $revision = self::get_object(object::$revision_list, $list->getRevision(), REVISION);
        $size = self::get_object(object::$size_list, $list->getSize(), SIZE);
        $kind = self::get_object(object::$kind_list, $list->getKind(), KIND);

        self::disperse_info($author, $date, $kind, $name, null, $revision, $size);
    }

    public static function disperse_info($author, $date, $kind, $name, $operation, $revision, $size){
        if($name != null){
            $name->make_connections($author, $date, $kind, $name, $operation, $revision, $size);
        }
    }

    protected function add_to_list(&$list, $object){
        $index = 0;
        foreach ($list as $entry){
            $index++;
            if ($entry->isEqual($object)){
                return;
            }
        }
        if($object != null){
            $list[$index] = $object;
        }
    }

    private static function get_object(&$list, $element, $type){
        $index = 0;
        foreach ($list as $entry){
            $index ++;
            if ($entry->isEqual($element)){
                return $entry;
            }
        }
        $new_object = self::get_new_object($element, $type);
        $list[$index] = $new_object;
        return $new_object;
    }

    protected function isEqual($object)
    {
        return (string)$this == (string)$object;
    }

    function __toString()
    {
        return (string)$this->object;
    }

    private static function get_new_object($element, $type){
        switch($type){
            case AUTHOR:
                return new author($element);
            case DATE;
                return new date($element);
            case KIND;
                return new kind($element);
            case NAME;
                return name::find_node($element);
            case OPERATION;
                return new operation($element);
            case REVISION;
                return new revision($element);
            case SIZE;
                return new size($element);
            default:
                return null;
        }
    }

    public static function getTeams($organization){
        if($organization){
            return $organization->getTeams();
        }
        $retVal = array();
        $organizations = self::getOrganizationList();
        foreach($organizations as $org){
            $teams = $org->getTeams();
            foreach($teams as $team){
                array_push($retVal, $team);
            }
        }
        return $retVal;
    }

    public static function getProjects($organization, $team){
        if($team){
            $projects = $team->getProjects();
            return $projects;
        }
        $retVal = array();
        $teams = self::getTeams($organization);
        foreach($teams as $team){
            $projects = $team->getProjects();

            foreach($projects as $project){
                array_push($retVal, $project);
            }
        }
        return $retVal;
    }

    public static function setOrganizationlist(){
        array_push(self::$organization_list, new organization("UIUC"));
    }

    /**
     * @return boolean
     */
    public static function getDisplayOrganization()
    {
        return self::$display_organization;
    }

    /**
     * @param boolean $display_organization
     */
    public static function setDisplayOrganization($display_organization)
    {
        foreach (self::getOrganizationList() as $org) {
            if((string)$org == $display_organization){
                self::$display_organization = $org;
            }
        }
    }

    /**
     * @return boolean
     */
    public static function getDisplayTeam()
    {
        return self::$display_team;
    }

    /**
     * @param boolean $display_team
     */
    public static function setDisplayTeam($display_team)
    {
        self::$display_team = $display_team;
    }

    /**
     * @return boolean
     */
    public static function getDisplayProject()
    {
        return self::$display_project;
    }

    /**
     * @param boolean $display_project
     */
    public static function setDisplayProject($display_project)
    {
        self::$display_project = $display_project;
    }

    /**
     * @return array
     */
    public static function getOrganizationList()
    {
        return self::$organization_list;
    }

    /**
     * @return mixed
     */
    public static function getRepoPath()
    {
        return self::$repo_path;
    }

    /**
     * @return array
     */
    public function getAuthors()
    {
        return $this->authors;
    }

    /**
     * @return array
     */
    public function getDates()
    {
        return $this->dates;
    }

    /**
     * @return array
     */
    public function getKinds()
    {
        return $this->kinds;
    }

    /**
     * @return array
     */
    public function getNames()
    {
        return $this->names;
    }

    /**
     * @return array
     */
    public function getOperations()
    {
        return $this->operations;
    }

    /**
     * @return array
     */
    public function getSizes()
    {
        return $this->sizes;
    }

    /**
     * @return array
     */
    public function getRevisions()
    {
        return $this->revisions;
    }

    /**
     * @return mixed
     */
    public function getObject()
    {
        return $this->object;
    }

    /**
     * @return array
     */
    public static function getAuthorList()
    {
        return self::$author_list;
    }

    /**
     * @return array
     */
    public static function getDateList()
    {
        return self::$date_list;
    }

    /**
     * @return array
     */
    public static function getKindList()
    {
        return self::$kind_list;
    }

    /**
     * @return array
     */
    public static function getNameList()
    {
        return self::$name_list;
    }

    /**
     * @return array
     */
    public static function getOperationList()
    {
        return self::$operation_list;
    }

    /**
     * @return array
     */
    public static function getRevisionList()
    {
        return self::$revision_list;
    }

    /**
     * @return array
     */
    public static function getSizeList()
    {
        return self::$size_list;
    }
}