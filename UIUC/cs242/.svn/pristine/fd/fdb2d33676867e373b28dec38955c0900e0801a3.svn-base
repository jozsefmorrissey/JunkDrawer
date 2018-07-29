<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/17/16
 * Time: 10:06 PM
 */
class revision
{
    private $operation;
    private $number;
    private $comment;
    private $date;

    /**
     * revision constructor.
     * @param $operation
     * @param $number
     * @param $comment
     */
    public function __construct($number)
    {
        $this->number = $number;
    }


    /**
     * @return mixed
     */
    public function getOperation()
    {
        return $this->operation;
    }

    /**
     * @param mixed $operation
     */
    public function setOperation($operation)
    {
        $this->operation = $operation;
    }

    /**
     * @return mixed
     */
    public function getNumber()
    {
        return $this->number;
    }

    /**
     * @param mixed $number
     */
    public function setNumber($number)
    {
        $this->number = $number;
    }

    /**
     * @return mixed
     */
    public function getComment()
    {
        return $this->comment;
    }

    /**
     * @param mixed $comment
     */
    public function setComment($comment)
    {
        $this->comment = $comment;
    }

    /**
     * @return mixed
     */
    public function getDate()
    {
        return $this->date;
    }

    /**
     * @param mixed $date
     */
    public function setDate($date)
    {
        $this->date = $date;
    }

    function __toString()
    {
            return (string)$this->number . ";" . $this->date . ";" . $this->operation . ";" . $this->comment;
    }
}