<?php

include 'project.php';

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/12/16
 * Time: 9:03 PM
 */
class team
{
    private $name;
    private $projects = array();
    private $image;
    private $discription;

    /**
     * team constructor.
     */
    public function __construct($team, $organization)
    {
        $this->name = $team;
        $this->get_projects($team, $organization);

    }

    private function get_projects($team, $organization){
        $node = $this->get_start_point($team, $organization);
        foreach($node->getChildren() as $project){
            array_push($this->projects, new project($project));
        }
    }

    private function get_start_point($team, $organization){
        if("UIUC" == (string)$organization){
            return traverse_tree::search_children($team, "jtmorri2");
        }
        return $team;
    }

    function __toString()
    {
        return (string)$this->name;
    }

    /**
     * @return mixed
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @return array
     */
    public function getProjects()
    {
        return $this->projects;
    }

    /**
     * @return mixed
     */
    public function getDiscription()
    {
        return $this->discription;
    }

    /**
     * @return mixed
     */
    public function getImage()
    {
        return $this->image;
    }
}