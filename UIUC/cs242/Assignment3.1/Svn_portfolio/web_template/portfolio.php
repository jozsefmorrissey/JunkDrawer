
<!DOCTYPE html>
<html lang="en">

<?php
    include "header.php";
    include "../init_data_structure.php";
    include "../control/control_functions.php";

    init_data_structure::init_data();
    object::setOrganizationlist();
    $organizations = object::getOrganizationList();

    $myDataBase = control_functions::connect();

    $state_id = $_GET['state_id'];
    $org_index = $_GET['org_index'];
    $team_index = $_GET['team_index'];

    $state_info = control_functions::get_state_info($myDataBase, $state_id);

    function getProjects($org_index, $team_index){
        if($org_index == -1){
            return object::getProjects(null, null);
        }
        elseif($team_index == -1){
            $organization = object::getOrganizationList()[$org_index];
            return object::getProjects($organization, null);
        }
        else{
            $organization = object::getOrganizationList()[$org_index];
            $team = object::getTeams($organization)[$team_index];
            return object::getProjects($organization, $team);
        }
    }
?>



<body>
<section class="main-section paddind" id="Portfolio"><!--main-section-start-->
    <div class="container">
        <h2>Portfolio</h2>
        <h6>Fresh portfolio of designs that will keep you wanting more.</h6>
        <div class="portfolioFilter">
            <ul class="Portfolio-nav wow fadeIn delay-02s">

                <?php
                echo "<li><a href=\"../control/control.php?view=portfolio&state_id=" . $state_id  . "&org_index=-1&team_index=-1\" data-filter=\"*\" class=\"current\" >All Organizations</a></li>";
                if ($org_index != -1){
                    echo "<li><a href=\"../control/control.php?view=portfolio&state_id=" . $state_id . "&org_index=" . $org_index . "&team_index=-1\" data-filter=\"*\" class=\"current\" >All Teams</a></li>";
                }
                if($org_index == -1){
                        for($index = 0; $index < sizeof($organizations); $index++){
                            echo "<li><a href=\"../control/control.php?view=portfolio&state_id=" . $state_id . "&org_index=" . $index . "&team_index=-1\" data-filter=\"*\" class=\"current\" >" . $organizations[$index] . "</a></li>";
                        }
                    }
                    else if ($team_index == -1){
                        $teams = object::getTeams($organizations[$org_index]);
                        for($index = 0; $index < sizeof($teams); $index++){
                            echo "<li><a href=\"../control/control.php?view=portfolio&state_id=" . $state_id . "&org_index=" . $index . "&team_index=" . $index . "\" data-filter=\"*\" class=\"current\" >" . $teams[$index] . "</a></li>";
                        }
                    }
                ?>
            </ul>
        </div>

    </div>
    <div class="portfolioContainer wow fadeInUp delay-04s">
        <?php
            $projects = getProjects($org_index, $team_index);
            $index = 0;
            for($index = 0; $index<sizeof($projects); $index++){
                $project = $projects[$index];
                echo " <div class=\" Portfolio-box printdesign\">
            <a href=\"../control/control.php?view=code_display&state_id=" . $state_id . "&org_index=" . $org_index . "&team_index=" . $team_index . "&project_index=" . $index . "\" data-filter=\"*\" class=\"current\" >" . "<img src=\"" . $project->getImage() . "\" alt=\"\" ></a>
            <h3> " .  $project . " </h3>
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