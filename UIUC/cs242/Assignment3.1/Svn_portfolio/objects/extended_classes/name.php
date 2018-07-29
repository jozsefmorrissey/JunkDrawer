<?php

include __DIR__  . '/../comment.php';
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/7/16
 * Time: 4:03 AM
 */
class name extends object
{
    private $parent;
    private $children = array();
    private $comments = array();

    public function __construct($object)
    {
        parent::__construct($object);
        array_push($this->comments, new comment((string)$this->getObject(), "I have something to say<br>Not really...", gettimeofday(True)));
        $this->comments[0]->addComment(new comment("sub-comment", "I like to talk about nothing", gettimeofday(True)));
    }


    protected function get_list()
    {
        return $this->names;
    }

    protected function make_connections($author, $date, $kind, $name, $operation, $revision, $size)
    {
        self::add_to_list($this->authors, $author);
        self::add_to_list($this->dates, $date);
        self::add_to_list($this->kinds, $kind);
        self::add_to_list($this->operations, $operation);
        self::add_to_list($this->revisions, $revision);
        self::add_to_list($this->sizes, $size);
    }

    function __toString()
    {
        $path = $this->getObject();
        $sub_loc = strtok($path, "/");
        while($sub_loc){
            $path = $sub_loc;
            $sub_loc = strtok("/");
        }
        return (string)$path;
    }

    public function getObject()
    {
        return parent::getObject(); // TODO: Change the autogenerated stub
    }

    public static function find_node($path){
        if(self::$root_node == null){
            self::$root_node = new name("root");
        }
        $current = self::$root_node;
        $names = name::get_path_array($path);
        foreach($names as $name){
            $current = self::find_child($name, $current);
        }
        return $current;
    }

    public static function get_path_array($path){
        $path_array = array();

        $index = 0;
        $sub_loc = strtok($path, "/");
        while($sub_loc){
            $path_array[$index++] = $sub_loc;
            $sub_loc = strtok("/");
        }
        return $path_array;
    }

    public static function find_child($name, $parent)
    {
        $index = 0;
        foreach($parent->children as $child){
            if((string)$child == (string)$name){
                return $child;
            }
            $index++;
        }
        $new_child = new name($name);
        $parent->children[$index] = $new_child;
        $new_child->set_parent($parent);
        //array_push(self::getNameList(), $new_child);
        $name_list = self::getNameList();
        self::$name_list[sizeof($name_list)] = $new_child;
        return $new_child;
    }

    public function addComment($comment){
        array_push($comments, $comment);
    }

    /**
     * @return array
     */
    public function getComments()
    {
        return $this->comments;
    }

    /**
     * @return array
     */
    public function getChildren()
    {
        return $this->children;
    }

    /**
     * @return mixed
     */
    public function getParent()
    {
        return $this->parent;
    }

    private function set_parent($parent){
        $this->parent = $parent;
    }
}