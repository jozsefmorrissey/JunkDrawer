<?php
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/16/16
 * Time: 2:24 AM
 */
include "../init_data_structure.php";

init_data_structure::init_data();
object::setOrganizationlist();

$orgs = object::getOrganizationList();
echo $orgs[0];

$teams = object::getTeams($orgs[0]);
echo $teams[0];

$projects = object::getProjects($orgs[0], $teams[0]);
echo $projects[0];

$projects = object::getProjects(null, null);
echo $projects[0];