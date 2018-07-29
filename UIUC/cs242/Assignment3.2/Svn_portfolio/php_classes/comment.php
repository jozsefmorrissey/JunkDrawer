<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/17/16
 * Time: 2:30 PM
 */
class comment
{
    private $username;
    private $message;
    private $timeStamp;
    private $comments = array();

    /**
     * comment constructor.
     * @param $username
     * @param $message
     * @param $timeStamp
     */
    public function __construct($username, $message, $timeStamp)
    {
        $this->username = $username;
        $this->message = $message;
        $this->timeStamp = $timeStamp;
    }


    public function addComment($comment){
        array_push($this->comments, $comment);
    }

    /**
     * @return array
     */
    public function getComments()
    {
        return $this->comments;
    }

    /**
     * @return mixed
     */
    public function getUsername()
    {
        return $this->username;
    }

    /**
     * @return mixed
     */
    public function getMessage()
    {
        return $this->message;
    }

    /**
     * @return mixed
     */
    public function getTimeStamp()
    {
        return $this->timeStamp;
    }


}