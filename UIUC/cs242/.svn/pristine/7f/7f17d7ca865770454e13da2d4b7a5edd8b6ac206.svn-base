<?php
include '../svn/parse.php';
include '../objects/object.php';

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/12/16
 * Time: 10:39 PM
 */
class init_data_structure
{
    public static function init_data(){

        $svn_lists = parse_svn::parse_list('/../xml_files/sp16_cs242_svn_list.xml');
        foreach ($svn_lists as $listEntry) {
            object::add_list($listEntry);
        }
        $svn_logs = parse_svn::parse_log('/../xml_files/sp16_cs242_svn_log.xml');
        foreach($svn_logs as $logEntry){
            object::add_log($logEntry);
        }
        /*$svn_lists = parse_svn::parse_list('/../xml_files/fa15_cs241_svn_list.xml');
        foreach ($svn_lists as $listEntry) {
            object::add_list($listEntry);
        }

        $svn_logs = parse_svn::parse_log('/../xml_files/fa15_cs241_svn_log.xml');
        foreach($svn_logs as $logEntry){
            object::add_log($logEntry);
        }

        $svn_lists = parse_svn::parse_list('/../xml_files/sp15-cs225_svn_list.xml');
        foreach ($svn_lists as $listEntry) {
            object::add_list($listEntry);
        }
        $svn_logs = parse_svn::parse_log('/../xml_files/sp15_cs225_svn_log.xml');
        foreach($svn_logs as $logEntry){
            object::add_log($logEntry);
        }*/
    }
}