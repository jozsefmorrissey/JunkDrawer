<!DOCTYPE html>

<html lang="en">

<?php
include "header.php";
require_once "../init_data_structure.php";
require_once "../control/control_functions.php";
require_once '../svn/file_data.php';


init_data_structure::init_data();
object::setOrganizationlist();
$organizations = object::getOrganizationList();

$myDataBase = control_functions::connect();
$org_index = $_GET['org_index'];
$team_index = $_GET['team_index'];
$state_id = $_GET['state_id'];
$project_index = $_GET['project_index'];

$GLOBALS['get_vars'] = "view=code_display" . "&state_id=" . $state_id . "&org_index=" . $org_index . "&team_index=" . $team_index . "&project_index=" . $project_index;

//$state_info = control_functions::get_state_info($myDataBase, $state_id);

$organization = $organizations[$org_index];
$team = object::getTeams($organization)[$team_index];
$project = getProjects($org_index, $team_index)[$project_index];


function getProjects($org_index, $team_index){
    if($org_index == -1){
        return object::getProjects(null, null);
    }
    elseif($team_index == -1){
        $organization = object::getOrganizationList()[$org_index];
        return object::getProjects($organization, null);
    }
    else{
        $organization = object::getOrganizationList()[$org_index];
        $team = object::getTeams($organization)[$team_index];
        return object::getProjects($organization, $team);
    }
}

function clean_variables($name, $layer){
        $var_name = $name . $name->getParent() . $layer;
    return str_replace(".", "_", $var_name);
}

function display_children($directory, $layer){
    $variable = clean_variables($directory, $layer);
    $show = $_GET[$variable];
    $kind = $directory->getKinds()[0];
    if($show == 1){
        echo "<lu id='directory_element_display'><a href=\"../control/control.php?" . $GLOBALS['get_vars'] . "&" . $variable . "=0" . "\" data-filter=\"*\" class=\"current\" >" . tab($layer) . $directory ."</a></lu>";
        $GLOBALS['get_vars'] .= "&" . $variable . "=1";
        if($kind == "dir"){
            $subDirectories = $directory->getChildren();
            for($index = 0; $index < sizeof($subDirectories); $index ++){
                echo "<br><br>";
                display_children($subDirectories[$index], $layer + 1);
            }
        }
        elseif($kind == "file"){
            $GLOBALS['get_vars'] .= "&" . $variable . "=0";
            $GLOBALS['display comment'] = $directory;
        }
    }
    else{
        echo "<lu id='directory_element_display'><a href=\"../control/control.php?" . $GLOBALS['get_vars'] . "&" . $variable . "=1" . "\" data-filter=\"*\" class=\"current\" >" . tab($layer) . $directory ."</a></lu>";
    }
    //echo "<br>";
}

function display_file($file)
{
    $url = file_data::create_url($file) . "/" . $file;
    //echo "<div style=\"position:relative; align='right'; top:20%; left:35%; width: 100%; bottom: 50px; border: 5px \">";
    echo "<iframe src=\"" . $url . "\" height= \"700px\" width=\"100%\"></iframe>";
    //echo "</div>";
}

function display_comments($file, $nest){
    tab($nest);
    echo "<div id='comment'>";
    foreach($file->getComments() as $comment){
        echo "<div id='single_comment_display'>";
            echo tab($nest) . "<h3>" . $comment->getUsername() . "</h3>" .
                 tab($nest) .  "<p>" . $comment->getTimeStamp() . "<p>" .
                 tab($nest) . "<p>" . $comment->getMessage() . "<p>";
            echo "<button id=\"comment_button\">Comment</button>";
            echo "</div>";
        display_comments($comment, $nest + 1);

    }
    echo "</div>";
}

function tab($number){
    $number = $number * 6;
    for($index = 0; $index < $number; $index++){
        echo "&nbsp";
    }
}

?>

<body>
<h1><?php echo $project ?> </h1>
<?php
    $first_layer = 0;
    $directory = $project->getProject();
    echo "<div id='project_display'>";
        echo "<div id='code_display'>";
            echo "<div id='directory_display'>";
                display_children($directory, $first_layer);
            echo "</div>";

            echo "<div id='file_display'>";
                if($GLOBALS['display comment']){
                    display_file($GLOBALS['display comment']);
                }
            echo "</div>";
        echo "</div>";
        echo "<div id='code_display'>";
            if($GLOBALS['display comment']){
                echo "<h2>Comments Section</h2>";
                echo "<button id=\"comment_button\">Comment</button>";
                display_comments($GLOBALS['display comment'], 0);
            }
        echo "</div>";
    echo "</div>";
?>



</body>
</html>