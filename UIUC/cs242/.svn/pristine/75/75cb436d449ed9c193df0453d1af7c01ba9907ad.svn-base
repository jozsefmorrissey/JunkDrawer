<!DOCTYPE html>

<html lang="en">

<?php
include "header.php";
include "../php_classes/database_functions.php";
include "../php_classes/constants.php";
include "../svn/file_data.php";

$dataBase = database_functions::connect();
$project_path = $_GET['project_path'];
$project = database_functions::pull_entry($dataBase, $project_path);

$GLOBALS['get_vars'] = "view=code_display&project_path=" . $project_path;

function clean_variables($directory, $layer){
    $name = $directory[NAME] . $directory[SIZE] . $layer;
    return str_replace(".", "_", $name);
}

function display_children($directory, $layer, $dataBase){
    $variable = clean_variables($directory, $layer);
    $show = $_GET[$variable];
    $kind = $directory[KIND];
    if($show == 1){
        echo "<lu id='directory_element_display'><a href=\"../control/control.php?" . $GLOBALS['get_vars'] . "&" . $variable . "=0" . "\" data-filter=\"*\" class=\"current\" >" . tab($layer) . $directory[NAME] ."</a></lu>";
        $GLOBALS['get_vars'] .= "&" . $variable . "=1";
        if($kind == "dir"){
            $subDirectories = database_functions::get_children($dataBase, $directory);
            foreach($subDirectories as $subDirectory){
                echo "<br><br>";
                display_children($subDirectory, $layer + 1, $dataBase);
            }
        }
    }
    else{
        if($kind == 'file'){
            echo "<lu id='" . $directory[PATH] . "' class=\"file_button\" ><a type=\"submit\" href=\"#\">" . tab($layer) . $directory[NAME] ."</a></lu>";
        }
        else {
            echo "<lu id='directory_element_display'><a href=\"../control/control.php?" . $GLOBALS['get_vars'] . "&" . $variable . "=1" . "\" data-filter=\"*\" class=\"current\" >" . tab($layer) . $directory[NAME] . "</a></lu>";
        }
    }
}

function display_file($url)
{
    //echo "<iframe src=\"" . $url . "\" height= \"700px\" style=\"background: grey\" width=\"100%\"></iframe>";

    echo "<pre>";
    $path = file_data::get_current_version($url);
    echo $path;
    echo "here i am";
    include '../svn/code/svn_repo/sp16-cs242/jtmorri2/Assignment1.0/Chess_Game/src/Chess/MiscClasses/powerUps.java';
    echo "</pre>";

}

function tab($number){
    $number = $number * 6;
    for($index = 0; $index < $number; $index++){
        echo "&nbsp";
    }
}

?>

<body>
<div id="main_background">
<h1><?php echo $project[NAME] ?> </h1>
<?php
    $first_layer = 0;
    echo "<div id='project_display'>";
        echo "<div id='code_display'>";
            echo "<div id='directory_display'>";
                display_children($project, $first_layer, $dataBase);
            echo "</div>";

            echo "<div id='file_display' class='container'\">";
            echo "</div>";
        echo "</div>";
    echo "<div id='code_display'>";
?>

<?php
        $path = $project[PATH];
        $parent_id = 0;
        echo "    <div id=\"comments\">";
        include '../html_files/conceal_comment_creators.php';
            include '../php_classes/read_comments.php';
            echo "<div id='new_comment" . 0 . "'></div>";
            echo "</div>";
        echo "    </div>";
?>



    <script type="text/javascript">
        $(document).ready(function(){

            $(".file_button").bind('click', function(){
                bid = this.id;
                $("#file_display").load('../php_classes/display_file.php?url=' + String(bid));
            });
         });
    </script>

</div>
</body>
</html>