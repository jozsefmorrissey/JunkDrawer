<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/6/16
 * Time: 10:00 PM
 */
class Component
{
    private $operation;
    private $path;
    private $kind;

    /**
     * log_entry constructor.
     * @param $operation
     * @param $path_suffix
     */
    public function __construct($operation, $path_suffix, $kind)
    {
        $this->operation = $operation;
        $this->path  = "https://subversion.ews.illinois.edu/svn/" . $path_suffix;
        $this->kind = $kind;
    }

    /**
     * @return mixed
     */
    public function getOperation()
    {
        return $this->operation;
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
    public function getPath()
    {
        return $this->path;
    }

    function __toString()
    {
        // TODO: Implement __toString() method.
        return $this->getPath();
    }
}