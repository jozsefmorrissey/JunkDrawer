<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/13/16
 * Time: 9:07 PM
 */

class control_functions
{
    public static function connect(){
        $myDataBase = mysqli_connect('localhost', 'root', 'morrissey');
        mysqli_select_db($myDataBase, 'portfolio');
        return $myDataBase;
    }

    public static function sanitize_user_name($myDataBase, $username){
        return mysqli_real_escape_string($myDataBase, $username);
    }

    public static function get_user_info($myDataBase, $username, $password){
        $username = self::sanitize_user_name($myDataBase, $username);
        $password = self::sanitize_user_name($myDataBase, $password);

        $retValue = $myDataBase->query("SELECT `user_id`, `user_name`, `password`, `first_name`, `last_name`, `account_state` FROM `user` WHERE user_name = '$username' AND password = '$password'");
        //$retValue = $myDataBase->query("SELECT 'user_name', 'password' FROM ")
        if($retValue){
            return mysqli_fetch_array($retValue);
        }

        return false;
    }

    public static function get_state_info($myDataBase, $index){
        $state = $myDataBase->query("SELECT `data_tree`, `organization`, `team`, `project`, `current_user_id`, `state_id` FROM `state` WHERE state_id = $index");
        if(!$state){
            die("Invalid state id: " . $index . "\n" . mysqli_error($myDataBase));
        }
        return mysqli_fetch_array($state);
    }

    public static function initialize_state_data_base($myDataBase){
        for($index = 1; $index <= 1024; $index++){
            $myDataBase = self::connect();
            self::reset_state_at($myDataBase, $index);
        }
    }

    /**
     * @param $myDataBaseu
     * @param $index
     */
    private static function reset_state_at($myDataBase, $index)
    {
        //$update = $myDataBase->query("INSERT INTO `state`(`organization`, `team`, `project`, `current_user_id`) VALUES (-1,-1,-1,-2)");
        $update = $myDataBase->query("UPDATE `state` SET `organization`=-1,`team`=-1,`project`=-1,`current_user_id`=-2 WHERE $index");
        if (!$update)
            die("insert failed: " . mysqli_error($myDataBase));
    }

    public static function find_unused_state_info(mysqli $myDataBase){
        $state_info = $myDataBase->query("SELECT `state_id` FROM `state` WHERE current_user_id = -2");
        if($state_info){
            $state_id = mysqli_fetch_array($state_info)[0];
            $update = $myDataBase->query("UPDATE `state` SET `organization`=-1,`team`=-1,`project`=-1,`current_user_id`=-1 WHERE state_id = $state_id");
            if(!$update)
                die("Find new state failed 2: " . mysqli_error($myDataBase));
            return $state_id;
        }
        die("Find new state failed 1: " . mysqli_error($myDataBase));
    }

    public static function set_state_organization($myDataBase, $new_value, $state_id){
        $myDataBase->query("UPDATE `state` SET `organization`=$new_value WHERE state_id = $state_id");
    }

    public static function set_state_team($myDataBase, $new_value, $state_id){
        $myDataBase->query("UPDATE `state` SET `team`=$new_value WHERE state_id = $state_id");
    }

    public static function set_state_project($myDataBase, $new_value, $state_id){
        $myDataBase->query("UPDATE `state` SET `project`=$new_value WHERE state_id = $state_id");
    }

    public static function set_state_user($myDataBase, $new_value, $state_id){
        $myDataBase->query("UPDATE `state` SET `current_user_id`=$new_value WHERE state_id = $state_id");
    }

    public static function set_state_data_tree($myDataBase, $new_value, $state_id){
        $myDataBase->query("UPDATE `data_tree` SET `current_user_id`=$new_value WHERE state_id = $state_id");
    }

    public static function get_state_user($myDataBase, $state_id){
        $tree = $myDataBase->query("SELECT `data_tree` FROM `user` WHERE state_id = $state_id");
        if($tree){
            return mysqli_fetch_array($tree);
        }
    }
}