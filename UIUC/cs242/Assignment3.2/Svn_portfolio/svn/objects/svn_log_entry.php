<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/6/16
 * Time: 9:30 PM
 */
class svn_log
{   //Class variables
    private $date;
    private $author;
    private $components;
    private $message;
    private $revision;

    /**
     * @return mixed
     */
    public function getMessage()
    {
        return $this->message;
    }

    /**
     * svn_log constructor.
     * @param $date
     * @param $author
     * @param $logEntry
     */
    public function __construct($date, $author, $components, $message, $revision)
    {
        $this->date = $date;
        $this->author = $author;
        $this->components = $components;
        $this->message = $message;
        $this->revision = $revision;
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
    public function getComponents()
    {
        return $this->components;
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
    public function getRevision()
    {
        return $this->revision;
    }
}