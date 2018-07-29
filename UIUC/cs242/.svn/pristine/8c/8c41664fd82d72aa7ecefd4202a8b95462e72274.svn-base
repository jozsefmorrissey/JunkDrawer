<?php

include '../objects/object.php';
include '../svn/parse.php';
include '../svn/file_data.php';

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/12/16
 * Time: 1:58 PM
 */
class file_dataTest extends PHPUnit_Framework_TestCase
{
    public $url;
    public $path;

    public function createUrl()
    {
        $svn_lists = parse_svn::parse_list('/../Tests/xml_test_files/sp16-cs242_svn_list.xml');
        foreach ($svn_lists as $listEntry) {
            object::add_list($listEntry);
        }
        $svn_logs = parse_svn::parse_log('/../Tests/xml_test_files/sp16-cs242_svn_log.xml');
        foreach($svn_logs as $logEntry){
            object::add_log($logEntry);
        }

        $root = object::getRootNode();
        $name = $root->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0];
        $this->url = file_data::create_url($name);
        assert($this->url == "https://subversion.ews.illinois.edu/svn/sp16-cs242/jtmorri2/");
    }

    public function createPath(){
        $root = object::getRootNode();
        $name = $root->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0];
        $this->path = file_data::create_path($name);
    }

    public function testPullFile(){
        $this->createUrl();
        $this->createPath();

        $root = object::getRootNode();
        $name = $root->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[0]->getChildren()[2]->getChildren()[0];

        $opened = file_data::retrieve_file($name, 7141);

        if(!$opened){
            echo "Failed to pull file\n";
        }

        $file = fread($opened, 25);
        assert($file == "beef\nchicken\npork\nlamb\n");

        $opened = file_data::retrieve_file($name, -1);
        $file = fread($opened, 40);
        assert($file == "beef\nchicken\npork\nlamb\ncucumbers\n");
    }
}
