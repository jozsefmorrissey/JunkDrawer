<?php

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/24/16
 * Time: 2:16 PM
 */
require_once '../php_classes/database_functions.php';

$dataBase = database_functions::connect();
$nest = 1;

if(isset($_GET['path'])){
    $path = $_GET['path'];
}

/**
 * @param $comments
 * @param $nest
 */

display_comments($dataBase, 0, $nest, "", $path);

function display_comments($dataBase, $comment_id, $nest, $color, $path)
{
    $comments = database_functions::pull_comments($dataBase, $path, $comment_id);
    if($comments){
        if($color == 'blue'){
            $color = 'black';
        }
        else{
            $color = 'blue';
        }
        foreach ($comments as $comment) {
            generate_comment($dataBase, $nest, $color, $path, $comment);
        }
    }
    else{
        echo "FALSE\n";
    }
}

/**
 * @param $dataBase
 * @param $nest
 * @param $color
 * @param $path
 * @param $comment
 */
function generate_comment($dataBase, $nest, $color, $path, $comment)
{
    echo "<div class='comment_display' id='comment_display" . $comment['id'] . "' style=\"background: " . $color . "; padding: " . 20 . "px; padding-right: " . 0 . "px; margin: " . 20 . "px; margin-left: " . $nest * 20 . "px; margin-right: " . 0 . "px;\">";
    echo "<h3>" . $comment['user_name'] . "</h3>" .
        "<p>" . $comment['date'] . "<p>" .
        "<p>" . $comment['message'] . "<p>";
    $path = $comment[PATH];
    $parent_id = $comment['id'];
    include '../html_files/conceal_comment_creators.php';
    display_comments($dataBase, $comment['id'], $nest + 1, $color, $path);
    echo "<div id='new_comment" . $comment['id'] . "'></div>";
    //echo "<div></div>";
    echo "</div>";
}

function button_name($comment){
    return "comment_button" . $comment['id'];
}
?>

