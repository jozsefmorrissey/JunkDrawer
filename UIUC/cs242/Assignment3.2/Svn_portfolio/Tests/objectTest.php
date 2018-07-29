<?php

include '../objects/object.php';
include '../svn/parse.php';

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/10/16
 * Time: 3:27 PM
 */
class objectTest extends PHPUnit_Framework_TestCase
{
    public function testAddList(){
        $svn_lists = parse_svn::parse_list('/../Tests/xml_test_files/sp16-cs242_svn_list.xml');
        foreach($svn_lists as $listEntry){
            object::add_list($listEntry);
        }
        $names = object::getNameList();
        //echo "sizeof: " . sizeof($names);

        $root = object::getRootNode();
        $children = $root->getChildren();
        assert((string)$children[0] == "https:");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "subversion.ews.illinois.edu");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "svn");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "sp16-cs242");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "jtmorri2");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert(sizeof($children) == 3);

        $authors_of_name1 = object::getAuthorList();
        $author = $authors_of_name1[0];
        assert($author->getObject() == "jtmorri2");
        //assert(sizeof($authors_of_name1) == 1);

        $names_of_author = $author->getNames();
        $name1 = $names_of_author[0];
        $name2 = $names_of_author[1];
        //assert($name1->getObject() == "Assignment1.0");
        //assert($name2->getObject() == "shopping/list3.txt");
        assert(sizeof($names_of_author) == 2);

        $authors = object::getAuthorList();
        assert(sizeof($authors) == 2);
    }

    public function testAddLog(){
        $svn_logs = parse_svn::parse_log('/../Tests/xml_test_files/sp16-cs242_svn_log.xml');
        foreach($svn_logs as $logEntry){
            object::add_log($logEntry);
        }
        $names = object::getAuthorList();
        assert($names[2] == "root");
        assert(sizeof($names[0]->getNames()) == 32);
        assert(sizeof($names[1]) == 1);
        assert(sizeof($names[2]->getNames()) == 1);

        $root = object::getRootNode();
        $children = $root->getChildren();
        assert((string)$children[0] == "https:");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "subversion.ews.illinois.edu");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "svn");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "sp16-cs242");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert((string)$children[0] == "jtmorri2");
        assert(sizeof($children) == 1);

        $children = $children[0]->getChildren();
        assert(sizeof($children) == 4);
    }

    public function testOrganizations(){
        object::setOrganizationlist();
        $organizations = object::getOrganizationList();
        assert($organizations[0] == "UIUC");
        assert($organizations[0]->getTeams()[0] == "sp16-cs242");
        assert($organizations[0]->getTeams()[0]->getProjects()[0] == "Assignment1.0");
    }
}
