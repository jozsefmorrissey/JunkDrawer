<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/7/16
 * Time: 4:02 AM
 */
class kind extends object
{
    protected function get_list()
    {
        return $this->kinds;
    }

    protected function make_connections($author, $date, $kind, $name, $operation, $revision, $size)
    {
        self::add_to_list($this->authors, $author);
        self::add_to_list($this->dates, $date);
        self::add_to_list($this->names, $name);
        self::add_to_list($this->operations, $operation);
        self::add_to_list($this->revisions, $revision);
        self::add_to_list($this->sizes, $size);
    }
}