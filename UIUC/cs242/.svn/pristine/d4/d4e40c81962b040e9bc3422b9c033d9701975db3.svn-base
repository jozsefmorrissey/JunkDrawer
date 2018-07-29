<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/12/16
 * Time: 8:56 PM
 */
class project
{
    private $project;

    /**
     * projects constructor.
     * @param $projects
     */
    public function __construct($project)
    {
        $this->project = $project;
    }


    /**
     * @return mixed
     */
    public function getProject()
    {
        return $this->project;
    }

    function __toString()
    {
        return (string)$this->project;
    }

    /**
     * @return mixed
     */
    public function getDescription()
    {
        if(file_exists("../descriptions/" . $this->project))
            return "../objects/descriptions/" . $this->project;

        return "../objects/descriptions/default";
    }

    /**
     * @return mixed
     */
    public function getImage()
    {
        if(file_exists("../images/" . $this->project))
            return "../objects/images/" . $this->project;

        return "../objects/images/default.png";
    }
}