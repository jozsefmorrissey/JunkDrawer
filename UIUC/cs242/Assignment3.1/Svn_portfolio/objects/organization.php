<?php

include '../objects/team.php';
include '../objects/traverse_tree.php';

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/12/16
 * Time: 8:58 PM
 */
class organization
{
    private $name;
    private $teams = array();
    private $description;

    /**
     * classes constructor.
     */
    public function __construct($organization)
    {
        $this->name = $organization;
        if($organization == "UIUC"){
            $this->get_svn_uiuc_teams();
        }
    }

    private function get_svn_uiuc_teams(){
        $repo = $this->find_svn_uiuc_repo();
        if($repo){
            $this->add_teams($repo->getChildren());
        }
        else{
            return false;
        }
    }

    private function add_teams($team_list){
        foreach($team_list as $team){
            $this->add_team($team);
        }
    }

    private function add_team($team){
        array_push($this->teams, new team($team, $this));
    }



    private function find_svn_uiuc_repo(){
        $root = object::getRootNode();
        $root = traverse_tree::search_children($root, "https:");
        if($root){
            $root = traverse_tree::search_children($root, "subversion.ews.illinois.edu");
        }
        else{
            return false;
        }
        if($root){
            return traverse_tree::search_children($root, "svn");
        }
        else{
            return false;
        }
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
    public function getTeams()
    {
        return $this->teams;
    }

    /**
     * @return mixed
     */
    public function getDescription()
    {
        return $this->description;
    }
}