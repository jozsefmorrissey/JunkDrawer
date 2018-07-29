<?php
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/18/16
 * Time: 3:06 AM
 */
include '../svn/file_data.php';

$url = 'https://subversion.ews.illinois.edu/svn/sp16-cs242/jtmorri2/Assignment1.0/Chess_Game/Chess_Game.iml';

file_data::retrieve_file($url, "10368");