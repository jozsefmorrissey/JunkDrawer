<?php
include '../svn/parse.php';
//include '../objects/object.php';
include 'entry.php';
include 'revision.php';
include '../init/svn_list.php';
include '../init/init_svn_log.php';
include 'database_functions.php';

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/12/16
 * Time: 10:39 PM
 */
class init_data_structure
{
    private static $entries = array();

    public static function init_data(){
        $log_file_path = '/../xml_files/sp16-cs242_svn_log.xml';
        $list_file_path = '/../xml_files/sp16-cs242_svn_list.xml';

        svn_list::init_svn_list(self::$entries, $list_file_path);
        init_svn_log::init_svn_log(self::$entries, $log_file_path);

        /*$log_file_path = '/../xml_files/fa15-cs241_svn_log.xml';
        $list_file_path = '/../xml_files/fa15-cs241_svn_list.xml';

        svn_list::init_svn_list(self::$entries, $list_file_path);
        init_svn_log::init_svn_log(self::$entries, $log_file_path);

        $log_file_path = '/../xml_files/sp15-cs225_svn_log.xml';
        $list_file_path = '/../xml_files/sp15-cs225_svn_list.xml';

        svn_list::init_svn_list(self::$entries, $list_file_path);
        init_svn_log::init_svn_log(self::$entries, $log_file_path);
*/
        database_functions::write_entries(self::$entries);

        /*$child = self::$entries["https:"]->getChildren()[0];
        echo $child . "\n";
        echo self::$entries[$child]->getChildren()[0] . "\n";
        echo self::$entries[self::$entries[$child]->getChildren()[0]]->getChildren()[2];
        */
    }
}