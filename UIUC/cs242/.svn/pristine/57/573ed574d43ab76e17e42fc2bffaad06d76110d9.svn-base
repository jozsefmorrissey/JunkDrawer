<?php

//Date Constants
const YEAR = "year";
const MONTH = "month";
const DAY = "day";
const HOUR = "hour";
const MINUTE = "minute";
const SECOND = "second";
const FRACTION = "fraction";
const WARNING_COUNT = "warning_count";
const WARNINGS = "warnings";
const ERROR_COUNT = "error_count";
const ERRORS = "errors";
const IS_LOCAL_TIME = "is_localtime";

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/7/16
 * Time: 1:48 AM
 */
class date extends object
{
    protected function get_list()
    {
        return $this->dates;
    }

    protected function make_connections($author, $date, $kind, $name, $operation, $revision, $size)
    {
        self::add_to_list($this->authors, $author);
        self::add_to_list($this->kinds, $kind);
        self::add_to_list($this->names, $name);
        self::add_to_list($this->operations, $operation);
        self::add_to_list($this->revisions, $revision);
        self::add_to_list($this->sizes, $size);
    }

    private $date;

    /**
     * date constructor.
     */
    public function __construct($date)
    {
        $this->object = $date;
        $this->date = date_parse((string)$date);
    }

    public function getYear()
    {
        return $this->date[YEAR];
    }

    public function getMonth()
    {
        return $this->date[MONTH];
    }

    public function getDay()
    {
        return $this->date[DAY];
    }

    public function getHour()
    {
        return $this->date[HOUR];
    }

    public function getMinute()
    {
        return $this->date[MINUTE];
    }

    public function getSecond()
    {
        return $this->date[SECOND];
    }

    public function getFraction()
    {
        return $this->date[FRACTION];
    }

    public function getWarningCount()
    {
        return $this->date[WARNING_COUNT];
    }

    public function getWarnins()
    {
        return $this->date[WARNINGS];
    }

    public function getErrorCount()
    {
        return $this->date[ERROR_COUNT];
    }

    public function getErrors()
    {
        return $this->date[ERRORS];
    }

    public function getIsLocalTime()
    {
        return $this->date[IS_LOCAL_TIME];
    }
}