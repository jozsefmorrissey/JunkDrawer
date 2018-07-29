
<?php   $comment_create = "comment_button" . $parent_id;
        $comment_editor = "comment_editor" . $parent_id;
        $comment_submit = "comment_submit" . $parent_id;
?>

<div id="<?php echo $comment_editor; ?>" style="display: none;"></section><!--business-talking-end-->
    <?php
            include 'create_comment.php';
    ?>
</div>


<div style="padding: 20px;">
    <?php
    echo "<button id=\"" . $comment_create . "\" class=\"input-btn\" type=\"submit\" value=\"Comment\">Comment</button>";
    ?>
</div>


<script type="text/javascript">
    $(document).ready(function(){

        $("#<?php echo $comment_create;?>").click(function(){
            showpopup(<?php echo $parent_id; ?>);
        });
        $("#<?php echo $comment_submit;?>").click(function(){
            hidepopup(<?php echo $parent_id; ?>);
        });

    });


    function showpopup($button_id)
    {
        $comment_editor = "comment_editor" + $button_id;
        $comment_display = "comment_display" + $button_id;

        $("#" + $comment_editor).fadeIn();
        $("#" + $comment_editor).css({"visibility":"visible","display":"block"});
        $('html, body').animate({ //Found this and the next two lines @: http://stackoverflow.com/questions/6677035/jquery-scroll-to-element
            scrollTop: $("#" + $comment_display).offset().top
        }, 1000);
    }

    function hidepopup($button_id)
    {
        $comment_editor = "comment_editor" + $button_id;
        $comment_location = "new_comment" + $button_id;
        var c_parent_id = document.getElementById("comment_parent_id" + $button_id).value;
        var c_username = document.getElementById("comment_username" + $button_id).value;
        var c_email = document.getElementById("comment_email" + $button_id).value;
        var c_date = new Date();

        var c_path = document.getElementById("comment_path" + $button_id).value;
        var c_message = document.getElementById("comment_message" + $button_id).value;


        $("#" + $comment_location).load("../Ajax/display_new_comment.php?message=" + encodeURI(c_message) + "&username=" + encodeURI(c_username) + "&parentid=" + encodeURI(c_parent_id) + "&date=" + encodeURI(c_date) + "&path=" + encodeURI(c_path) + "&email=" + encodeURI(c_email));

        $('html, body').animate({ //Found this and the next two lines @: http://stackoverflow.com/questions/6677035/jquery-scroll-to-element
            scrollTop: $("#" + $comment_location).offset().top - 500
        }, 1000);

        $("#" + $comment_editor).fadeOut();
        $("#" + $comment_editor).css({"visibility":"hidden","display":"none"});
    }
</script>
