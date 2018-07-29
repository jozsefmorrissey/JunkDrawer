<?php
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/13/16
 * Time: 8:29 AM
 */

include '../constants.php';

$view = $_GET['view'];
$state_id = $_GET['state_id'];
$against = 'code_display';
    switch($view){
        case PORTFOLIO:
            $view_path = "portfolio.php";
            break;
        case ABOUT:
            $view_path = "image_right.php";
            break;
        case LANGUAGES:
            $view_path = "image_left.php";
            break;
        case ACKNOWLEDGE:
            $view_path = "team.php";
            break;
        case HOME:
            $view_path = 'main_page.php';
            break;
        case CONTACT:
            $view_path = "contact.php";
            break;
        case $against:
            $view_path = "project_display.php";
            break;
        default:
            $view_path = 'main_page.php';
    }

    include "../web_template/" . $view_path;










