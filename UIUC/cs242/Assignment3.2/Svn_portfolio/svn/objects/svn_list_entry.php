<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/7/16
 * Time: 1:43 AM
 */
class svn_list_entry
{
    private $path;
    private $kind;
    private $name;
    private $size;
    private $revision;
    private $author;
    private $date;

    /**
     * svn_list_entry constructor.
     * @param $kind
     * @param $name
     * @param $size
     * @param $revision
     * @param $author
     * @param $date
     */
    public function __construct($kind, $name, $size, $revision, $author, $date, $path)
    {
        $this->path = $path;
        $this->kind = $kind;
        $this->name = $path . "/" . $name;
        $this->size = $size;
        $this->revision = $revision;
        $this->author = $author;
        $this->date = $date;
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
    public function getKind()
    {
        return $this->kind;
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
    public function getRevision()
    {
        return $this->revision;
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
    public function getPath()
    {
        return $this->path;
    }
}