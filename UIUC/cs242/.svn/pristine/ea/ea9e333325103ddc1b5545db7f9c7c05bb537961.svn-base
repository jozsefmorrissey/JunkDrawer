<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/17/16
 * Time: 9:33 PM
 */
class entry
{
    private $path;
    private $name;
    private $author;
    private $date;
    private $kind;
    private $operation;
    private $size;
    private $organization;
    private $team;
    private $descriptions;
    private $parent;
    private $children = array();
    private $revisions = array();

    /**
     * entry constructor.
     * @param $author
     * @param $date
     * @param $kind
     * @param $operation
     * @param array $revision
     * @param $size
     * @param $organization
     * @param $team
     * @param $discription
     */
    public function __construct($parent, $path, $name, $author, $date, $kind, $revision, $size, $organization, $team, $discription)
    {
        $this->parent = $parent;
        $this->path = $path;
        $this->name = $name;
        $this->author = $author;
        $this->date = $date;
        $this->kind = $kind;
        if($revision != null){
            $this->revisions[(string)$revision->getNumber()] = $revision;
        }
        $this->size = $size;
        $this->organization = self::getOrganizationName($organization);
        $this->team = self::getTeamName($team);
        $this->descriptions = $discription;
    }

    function __toString()
    {
        return (string)$this->name;
    }

    private static function getTeamName($team){
        if(strpos($team, '-') == 4){
            return substr($team, 5);
        }
        return $team;
    }

    private static function getOrganizationName($organization){
        if($organization == 'subversion.ews.illinois.edu'){
            return 'UIUC';
        }
        return $organization;
    }

    /**
     * @return mixed
     */
    public function getAuthor()
    {
        return $this->author;
    }

    /**
     * @return mixed
     */
    public function getDate()
    {
        return $this->date;
    }

    /**
     * @return mixed
     */
    public function getKind()
    {
        return $this->kind;
    }

    /**
     * @return mixed
     */
    public function getOperation()
    {
        return $this->operation;
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
    public function getSize()
    {
        return $this->size;
    }

    /**
     * @return mixed
     */
    public function getOrganization()
    {
        return $this->organization;
    }

    /**
     * @return mixed
     */
    public function getTeam()
    {
        return $this->team;
    }

    /**
     * @return mixed
     */
    public function getDescriptions()
    {
        return $this->descriptions;
    }

    /**
     * @param mixed $descriptions
     */
    public function setDescriptions($descriptions)
    {
        $this->descriptions = $descriptions;
    }

    /**
     * @param array $revisions
     */
    public function addToRevisions($revision)
    {
        $this->revisions[(string)$revision->getNumber()] = $revision;
    }

    /**
     * @return mixed
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @return mixed
     */
    public function getPath()
    {
        return $this->path;
    }

    /**
     * @return mixed
     */
    public function getParent()
    {
        return $this->parent;
    }

    /**
     * @return array
     */
    public function getChildren()
    {
        return $this->children;
    }

    /**
     * @param array $children
     */
    public function addToChildren($child)
    {
        array_push($this->children, $child);
    }
}