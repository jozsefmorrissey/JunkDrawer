<?php
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/26/16
 * Time: 4:25 PM
 */
include '../svn/file_data.php';
include '../php_classes/database_functions.php';
?>

    <?php
    //echo "<div class='container'>";
        echo "<div class='scrollable'>";
            echo "<pre id='file_contents'> ";
            $url = $_GET['url'];
            $revision = $_GET['revision'];
            echo "url, revis: '" . $url ."','" . $revision . "'<br>";

            $path = file_data::get_current_version($url);

            echo file_get_contents($path);
            echo "</pre>";
        echo "</div>";

        $dataBase = database_functions::connect();
        $entry = database_functions::pull_entry($dataBase, $url);
        $revisions = parse_revisions($entry['revisions']);

        echo "<div style='position: absolute; left: 50%; margin-left: -150px;'>";
        $count = 0;
        foreach($revisions as $revision){
            if($count++ %10 == 0){
                echo "<br><br><br>";
            }
            echo "<lu ><a href=\"#\" class=\"revision_btn\" id='" . $path  . "&revision=" . $revision . "'>" . $revision . "</a></lu>";
        }
        echo "</div>";
    //echo "</div>";



    function parse_revisions($revisions){
        $revision = strtok($revisions, ",");
        $rev_array = array();
        while($revision){
            $rev_number = substr($revision, 0, strpos($revision, ";"));
            array_push($rev_array, $rev_number);
            $revision = strtok(",");
        }
        return $rev_array;
    }
    ?>

<script type="text/javascript">
    $(document).ready(function(){

        $(".revision_btn").bind('click', function(){
            bid = this.id;
            window.alert('../Ajax/display_file.php?url=' + bid);
            $("#file_display").load('../Ajax/display_file.php?url=' + String(bid));
        });
    });
</script>
