
<!DOCTYPE html>
<html lang="en">

<?php
    include "header.php";
    include "../control/control_functions.php";
    include '../php_classes/database_functions.php';
    include '../php_classes/constants.php';

    $dataBase = control_functions::connect();

    $org_path = $_GET['org_path'];
    $team_path = $_GET['team_path'];

    $organizations = database_functions::get_organizations($dataBase, $org_path);
    $teams = database_functions::get_teams($dataBase, $team_path, $org_path);
    $projects = database_functions::getProjects($dataBase, $team_path, $org_path);

?>



<body>
<section class="main-section paddind" id="Portfolio"><!--main-section-start-->
    <div class="container">
        <h2>Portfolio</h2>
        <h6>Fresh portfolio of designs that will keep you wanting more.</h6>
        <div class="portfolioFilter">
            <ul class="Portfolio-nav wow fadeIn delay-02s">
                <li><a href="../control/control.php?view=portfolio&org_path=-1&team_path=-1" data-filter="*" class="current"> All Organizations</a> </li>
                <li><a href="../control/control.php?view=portfolio&org_path=<?php echo $org_path ?>&team_path=-1" data-filter="*" class="current"> All Teams</a> </li>
                <?php
                if($org_path == -1){
                    foreach($organizations as $organization){
                        echo "<li><a href=\"../control/control.php?view=portfolio&" . "&org_path=" . $organization[PATH] . "&team_path=-1\" data-filter=\"*\" class=\"current\" >" . $organization[ORGANIZATION] . "</a></li>";
                        }
                    }
                    else if ($team_path == -1){
                        foreach($teams as $team){
                            echo "<li><a href=\"../control/control.php?view=portfolio&" . "&org_path=" . $org_path . "&team_path=" . $team[PATH] . "\" data-filter=\"*\" class=\"current\" >" . $team[TEAM] . "</a></li>";
                        }
                    }
                ?>
            </ul>
        </div>

    </div>
    <div class="portfolioContainer wow fadeInUp delay-04s">
        <?php
            $unspecified_org = ($org_path == -1);
            $unspecified_team = ($team_path == -1);
            foreach($projects as $project){
                echo " <div class=\" Portfolio-box printdesign\">
                <a href=\"../control/control.php?view=code_display&project_path=" . $project[PATH] . "\" data-filter=\"*\" class=\"current\" >" . "<img src=\"" . "../images/iphone.png" . "\" alt=\"\" ></a>
                <h3> " .  $project[NAME] . " </h3>
                <p></p>
                </div>";
            }
        ?>
    </div>
</section><!--main-section-end-->
</body>
<body>

<?php //include '../web_template/footer.php'; ?>

</body>
</html>