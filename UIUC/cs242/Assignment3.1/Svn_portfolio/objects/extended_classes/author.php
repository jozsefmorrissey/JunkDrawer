<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/7/16
 * Time: 4:03 AM
 */
class author extends object
{
    protected function get_list()
    {
        return $this->authors;
    }

    protected function make_connections($author, $date, $kind, $name, $operation, $revision, $size)
    {
        self::add_to_list($this->dates, $date);
        self::add_to_list($this->kinds, $kind);
        self::add_to_list($this->names, $name);
        self::add_to_list($this->operations, $operation);
        self::add_to_list($this->revisions, $revision);
        self::add_to_list($this->sizes, $size);
    }
}