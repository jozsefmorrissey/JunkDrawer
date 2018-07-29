<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/12/16
 * Time: 10:19 PM
 */
class traverse_tree
{
    /**
     * @param $root
     */
    public static function search_children($root, $node)
    {
        foreach ($root->getChildren() as $child) {
            if ((string)$child == $node) {
                return $child;
            }
        }
        return false;
    }
}