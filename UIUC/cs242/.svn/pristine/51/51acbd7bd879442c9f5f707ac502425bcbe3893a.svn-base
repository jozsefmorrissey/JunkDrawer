<?php

include '../svn/parse.php';

/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/10/16
 * Time: 1:26 PM
 */
class svn_logTest extends PHPUnit_Framework_TestCase
{
    public function testFile(){
        $this->testParseLog();
    }

    public function testParseLog()
    {   $svn_logs = parse_svn::parse_log('/../Tests/xml_test_files/sp16-cs242_svn_log.xml');
        assert($svn_logs[0]->getAuthor() == "jtmorri2");
        assert($svn_logs[0]->getAuthor() == "jtmorri2");
        assert($svn_logs[0]->getDate() == "2016-03-04T00:55:16.390802Z");
        assert($svn_logs[0]->getComponents()[0]->getPath() == "Assignment2.1");
        assert($svn_logs[sizeof($svn_logs) - 1]->getAuthor() == "root");
        assert($svn_logs[sizeof($svn_logs) - 1]->getDate() == "2016-01-21T17:21:39.231981Z");
        assert($svn_logs[sizeof($svn_logs) - 1]->getMessage() == "created directory jtmorri2");
    }

    public function testParseList()
    {   $svn_lists = parse_svn::parse_list('/../Tests/xml_test_files/sp16-cs242_svn_list.xml');

        assert($svn_lists[0]->getPath() == "https://subversion.ews.illinois.edu/svn/sp16-cs242/jtmorri2");

        assert($svn_lists[0]->getAuthor() == "jtmorri2");
        assert($svn_lists[0]->getDate() == "2016-02-26T00:59:06.561096Z");
        assert($svn_lists[0]->getRevision() == "7141");

        assert($svn_lists[sizeof($svn_lists) - 1]->getAuthor() == "jtmorri2");
        assert($svn_lists[sizeof($svn_lists) - 1]->getDate() == "2016-01-28T23:33:46.413030Z");
        assert($svn_lists[sizeof($svn_lists) - 1]->getRevision() == "2069");
    }

}
