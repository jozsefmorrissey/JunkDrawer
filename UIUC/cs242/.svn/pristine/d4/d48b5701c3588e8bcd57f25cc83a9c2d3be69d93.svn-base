<?php
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/13/16
 * Time: 8:29 AM
 */

include 'control_functions.php';

$GLOBALS['myDataBase'] = control_functions::connect();
$myDataBase = $GLOBALS['myDataBase'];

$state_id = control_functions::find_unused_state_info($myDataBase);

$_GET['state_id'] = $state_id;
$_GET['view'] = 'home';
$_GET['org_index'] = -1;
$_GET['team_index'] = -1;

$state_info = control_functions::get_state_info($myDataBase, $state_id);

include 'control.php';