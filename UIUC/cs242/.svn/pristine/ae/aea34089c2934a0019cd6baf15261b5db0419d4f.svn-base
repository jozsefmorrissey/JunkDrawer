<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/18/16
 * Time: 3:05 PM
 */
class database_functions
{
    public static function connect(){
        $myDataBase = mysqli_connect('localhost', 'root', 'morrissey');
        mysqli_select_db($myDataBase, 'portfolio');
        return $myDataBase;
    }

    public static function write_entries($entries){
        $dataBase = self::connect();
        foreach($entries as $entry){
            self::write_entry($entry, $dataBase);
        }
    }

    public static function write_entry(entry $entry, $dataBase){
        $path = $entry->getPath();
        $name = $entry->getName();
        $author = $entry->getAuthor();
        $date = $entry->getDate();
        $kind = $entry->getKind();
        $operation = $entry->getOperation();
        $organization = $entry->getOrganization();
        $team = $entry->getTeam();
        $size = $entry->getSize();
        $description = $entry->getDescriptions();
        $parent = $entry->getParent();
        $children = self::array_to_string($entry->getChildren());
        $revision = self::array_to_string($entry->getRevisions());
        $dataBase->query("INSERT INTO `svn_entry` SET `organization`='$organization', `team`='$team', `revisions`='$revision', `children`='$children', `parent`='$parent', `operation`='$operation', `path`='$path', `size`='$size', `description`='$description', `name`='$name', `author`='$author', `date`='$date', `kind`='$kind'");
    }

    public static function write_comment($dataBase, $username, $date, $message, $parent_id, $path){
        $username = mysqli_escape_string($dataBase, $username);
        $date = mysqli_escape_string($dataBase, $date);
        if(!$date){
            echo "<br>Danger will robinson Danger";
        }
        $message = mysqli_escape_string($dataBase, $message);
        $dataBase->query("INSERT INTO `comment` SET `user_name`='$username', `date`='$date', `message`='$message', `parent_id`='$parent_id', `path`='$path'");
    }

    private static function array_to_string($array){
        $string = "";
        foreach($array as $element){
            $string .= $element . ",";
        }
        return $string;
    }

    private static function string_to_array($string){
        $element = strtok($string, ",");
        $array = array();
        while($element){
            array_push($array, $element);
            $element = strtok(",");
        }
        return $array;
    }

    public static function get_revision_hash($project){
        $rev_string_arr = database_functions::string_to_array($project[REVISIONS]);
        return database_functions::revision_array_to_hash($rev_string_arr);
    }

    private static function revision_array_to_hash($array){
        $index = 0;
        $revision_array = array();
        foreach($array as $element){
            $revision_array[$index] = array();
            $revision_array[$index][REVISION_NUMBER] = strtok($element, ";");
            $revision_array[$index][DATE] = strtok(";");
            $revision_array[$index][OPERATION] = strtok(";");
            $revision_array[$index++][COMMENT] = strtok(";");
        }
        return $revision_array;
    }

    public static function get_organizations($dataBase, $organization_path){
        $organizations = array();
        $organization_path = trim($organization_path);
        if($organization_path == -1 or $organization_path == 'https://subversion.ews.illinois.edu/svn'){
            $uiuc = self::pull_entry($dataBase, 'https://subversion.ews.illinois.edu/svn');
            array_push($organizations, $uiuc);
        }
        return $organizations;
    }

    public static function get_teams($dataBase, $team_path, $organization_path){
        $organization_path = trim($organization_path);
        $team_path = trim($team_path);
        $organizations = self::get_organizations($dataBase, $organization_path);
        $teams = array();
        foreach($organizations as $organization){
            $org_teams = self::get_children($dataBase, $organization);
            foreach($org_teams as $org_team){
                if($team_path == -1 or $team_path == $org_team[PATH]){
                array_push($teams, $org_team);
                }
            }
        }
        return $teams;
    }

    public static function getProjects($dataBase, $team_path, $organization_path){
        $teams = self::get_teams($dataBase, $team_path, $organization_path);
        $projects = array();
        foreach($teams as $team){
            $author = self::get_children($dataBase, $team);
            $team_projects = self::get_children($dataBase, $author[0]);
            foreach($team_projects as $team_project){
                array_push($projects, $team_project);
            }
        }
        return $projects;
    }

    public static function get_children($dataBase, $entry){
        $children = self::string_to_array($entry['children']);
        $entryArray = array();
        foreach($children as $child){
            $child_entry = self::pull_entry($dataBase, $child);
            array_push($entryArray, $child_entry);
        }
        return $entryArray;
    }

    public static function sanitize_user_name($myDataBase, $username){
        return mysqli_real_escape_string($myDataBase, $username);
    }

    public static function get_user_info($myDataBase, $username, $password){
        $username = self::sanitize_user_name($myDataBase, $username);
        $password = self::sanitize_user_name($myDataBase, $password);

        $retValue = $myDataBase->query("SELECT `user_id`, `user_name`, `password`, `first_name`, `last_name`, `account_state` FROM `user` WHERE user_name = '$username' AND password = '$password'");
        if($retValue){
            return mysqli_fetch_array($retValue);
        }

        return false;
    }

    public static function pull_entry($dataBase, $path){
        $attribute = "`name`, `path`, `kind`, `description`, `author`, `organization`, `team`, `children`, `revisions`";
        $table = "`svn_entry`";
        $condition = "path='" . $path . "'";
        return mysqli_fetch_array(self::pull_from_data_base($dataBase, $attribute, $table, $condition));
    }

    public static function pull_comments($dataBase, $path, $parent_id){

        $attribute = "`user_name`, `path`, `date`, `message`, `id`, `parent_id`";
        $table = "`comment`";
        $condition = "path='" . $path . "' AND parent_id='" . $parent_id . "'";
        return self::pull_from_data_base($dataBase, $attribute, $table, $condition);
    }

    public static function pull_from_data_base($dataBase, $attribute, $table, $condition){
        $sql_cmd = "SELECT " . $attribute . " FROM " . $table . " WHERE ". $condition;
        $data = $dataBase->query($sql_cmd);
        if($data){
            return $data;
        }
        else{
            echo mysqli_error($dataBase);
            return false;
        }
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