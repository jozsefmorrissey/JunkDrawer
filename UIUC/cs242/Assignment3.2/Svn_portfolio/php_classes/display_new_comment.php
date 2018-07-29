<?php
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/24/16
 * Time: 2:02 AM
 */

//include '../html_files/header.php';
include '../php_classes/database_functions.php';

$username = $_GET['username'];
$date = $_GET['date'];
$message = clean_language($_GET['message']);
$parent_id = $_GET['parentid'];
$path = $_GET['path'];
$email = $_GET['email'];

echo "<div class='comment_display' id='comment_display" . $parent_id . "' style=\"background: green; padding: " . 20 . "px; margin: " . 20 . "px; margin-left: " . $nest * 20 . "px;\">";
echo "<h3>" . $username . "</h3>" .
    "<p>" . $date . "<p>" .
    "<p>" . $message . "<p>";

$dataBase = database_functions::connect();

database_functions::write_comment($dataBase, $username, $date, $message, $parent_id, $path);

function clean_language($dirty){
    $clean = $dirty;
    $evil_words = [["shit", "waste"], ["ass", "gluteus maximus"], ["dick","phallus"], ["mahogany canoe", "frozen improvised pleasure device made from excrement"], ["mung", "decomposed remains of a fetus inside of a deceased female"]];
    foreach($evil_words as $evil_word){
        $clean = str_replace($evil_word[0], $evil_word[1], $clean);
    }

    $waste = [ " different "," used "," important "," every "," large "," available "," popular "," able "," basic "," known "," various "," difficult "," several "," united "," historical "," hot "," useful "," mental "," scared "," additional "," emotional "," old "," political "," similar "," healthy "," financial "," medical "," traditional "," federal "," entire "," strong "," actual "," significant "," successful "," electrical "," expensive "," pregnant "," intelligent "," interesting "," poor "," happy "," responsible "," cute "," helpful "," recent "," willing "," nice "," wonderful "," impossible "," serious "," huge "," rare "," technical "," typical "," competitive "," critical "," electronic "," immediate "," aware "," educational "," environmental "," global "," legal "," relevant "," accurate "," capable "," dangerous "," dramatic "," efficient "," powerful "," foreign "," hungry "," practical "," psychological "," severe "," suitable "," numerous "," sufficient "," unusual "," consistent "," cultural "," existing "," famous "," pure "," afraid "," obvious "," careful "," latter "," unhappy "," acceptable "," aggressive "," boring "," distinct "," eastern "," logical "," reasonable "," strict "," administrative "," automatic "," civil "," former "," massive "," southern "," unfair "," visible "," alive "," angry "," desperate "," exciting "," friendly "," lucky "," realistic "," sorry "," ugly "," unlikely "," anxious "," comprehensive "," curious "," impressive "," informal "," inner "," pleasant "," sexual "," sudden "," terrible "," unable "," weak "," wooden "," asleep "," confident "," conscious "," decent "," embarrassed "," guilty "," lonely "," mad "," nervous "," odd "," remarkable "," substantial "," suspicious "," tall "," tiny "," more "," some "," one "," all "," many "," most "," other "," such "," even "," new "," just "," good "," any "," each "," much "," own "," great "," another "," same "," few "," free "," right "," still "," best "," public "," human "," both "," local "," sure "," better "," general "," specific "," enough "," long "," small "," less "," high "," certain "," little "," common "," next "," simple "," hard "," past "," big "," possible "," particular "," real "," major "," personal "," current "," left "," national "," least "," natural "," physical "," short "," last "," single "," individual "," main "," potential "," professional "," international "," lower "," open "," according "," alternative "," special "," working "," true "," whole "," clear "," dry "," easy "," cold "," commercial "," full "," low "," primary "," worth "," necessary "," positive "," present "," close "," creative "," green "," late "," fit "," glad "," proper "," complex "," content "," due "," effective "," middle "," regular "," fast "," independent "," original "," wide "," beautiful "," complete "," active "," negative "," safe "," visual "," wrong "," ago "," quick "," ready "," straight "," white "," direct "," excellent "," extra "," junior "," pretty "," unique "," classic "," final "," overall "," private "," separate "," western "," alone "," familiar "," official "," perfect "," bright "," broad "," comfortable "," flat "," rich "," warm "," young "," heavy "," valuable "," correct "," leading "," slow "," clean "," fresh "," normal "," secret "," tough "," brown "," cheap "," deep "," objective "," secure "," thin "," chemical "," cool "," extreme "," exact "," fair "," fine "," formal "," opposite "," remote "," total "," vast "," lost "," smooth "," dark "," double "," equal "," firm "," frequent "," internal "," sensitive "," constant "," minor "," previous "," raw "," soft "," solid "," weird "," amazing "," annual "," busy "," dead "," false "," round "," sharp "," thick "," wise "," equivalent "," initial "," narrow "," nearby "," proud "," spiritual "," wild "," adult "," apart "," brief "," crazy "," prior "," rough "," sad "," sick "," strange "," external "," illegal "," loud "," mobile "," nasty "," ordinary "," royal "," senior "," super "," tight "," upper "," yellow "," dependent "," funny "," gross "," ill "," spare "," sweet "," upstairs "," usual "," brave "," calm "," dirty "," downtown "," grand "," honest "," loose "," male "," quiet "," brilliant "," dear "," drunk "," empty "," female "," inevitable "," neat "," ok "," representative "," silly "," slight "," smart "," stupid "," temporary "," weekly "," that "," this "," what "," which "," time "," these "," work "," no "," only "," then "," first "," money "," over "," business "," his "," game "," think "," after "," life "," day "," home "," economy "," away "," either "," fat "," key "," training "," top "," level "," far "," fun "," house "," kind "," future "," action "," live "," period "," subject "," mean "," stock "," chance "," beginning "," upset "," chicken "," head "," material "," salt "," car "," appropriate "," inside "," outside "," standard "," medium "," choice "," north "," square "," born "," capital "," shot "," front "," living "," plastic "," express "," feeling "," otherwise "," plus "," saving "," animal "," budget "," minute "," character "," maximum "," novel "," plenty "," select "," background "," forward "," glass "," joint "," master "," red "," vegetable "," ideal "," kitchen "," mother "," party "," relative "," signal "," street "," connect "," minimum "," sea "," south "," status "," daughter "," hour "," trick "," afternoon "," gold "," mission "," agent "," corner "," east "," neither "," parking "," routine "," swimming "," winter "," airline "," designer "," dress "," emergency "," evening "," extension "," holiday "," horror "," mountain "," patient "," proof "," west "," wine "," expert "," native "," opening "," silver "," waste "," plane "," leather "," purple "," specialist "," bitter "," incident "," motor "," pretend "," prize "," resident"];
    $adjectives = [" abandoned "," able "," absolute "," adorable "," adventurous "," academic "," acceptable "," acclaimed "," accomplished "," accurate "," aching "," acidic "," acrobatic "," active "," actual "," adept "," admirable "," admired "," adolescent "," adorable "," adored "," advanced "," afraid "," affectionate "," aged "," aggravating "," aggressive "," agile "," agitated "," agonizing "," agreeable "," ajar "," alarmed "," alarming "," alert "," alienated "," alive "," all "," altruistic "," amazing "," ambitious "," ample "," amused "," amusing "," anchored "," ancient "," angelic "," angry "," anguished "," animated "," annual "," another "," antique "," anxious "," any "," apprehensive "," appropriate "," apt "," arctic "," arid "," aromatic "," artistic "," ashamed "," assured "," astonishing "," athletic "," attached "," attentive "," attractive "," austere "," authentic "," authorized "," automatic "," avaricious "," average "," aware "," awesome "," awful "," awkward "," babyish "," bad "," back "," baggy "," bare "," barren "," basic "," beautiful "," belated "," beloved "," beneficial "," better "," best "," bewitched "," big "," big-hearted "," biodegradable "," bite-sized "," bitter "," black "," black-and-white "," bland "," blank "," blaring "," bleak "," blind "," blissful "," blond "," blue "," blushing "," bogus "," boiling "," bold "," bony "," boring "," bossy "," both "," bouncy "," bountiful "," bowed "," brave "," breakable "," brief "," bright "," brilliant "," brisk "," broken "," bronze "," brown "," bruised "," bubbly "," bulky "," bumpy "," buoyant "," burdensome "," burly "," bustling "," busy "," buttery "," buzzing "," calculating "," calm "," candid "," canine "," capital "," carefree "," careful "," careless "," caring "," cautious "," cavernous "," celebrated "," charming "," cheap "," cheerful "," cheery "," chief "," chilly "," chubby "," circular "," classic "," clean "," clear "," clear-cut "," clever "," close "," closed "," cloudy "," clueless "," clumsy "," cluttered "," coarse "," cold "," colorful "," colorless "," colossal "," comfortable "," common "," compassionate "," competent "," complete "," complex "," complicated "," composed "," concerned "," concrete "," confused "," conscious "," considerate "," constant "," content "," conventional "," cooked "," cool "," cooperative "," coordinated "," corny "," corrupt "," costly "," courageous "," courteous "," crafty "," crazy "," creamy "," creative "," creepy "," criminal "," crisp "," critical "," crooked "," crowded "," cruel "," crushing "," cuddly "," cultivated "," cultured "," cumbersome "," curly "," curvy "," cute "," cylindrical "," damaged "," damp "," dangerous "," dapper "," daring "," darling "," dark "," dazzling "," dead "," deadly "," deafening "," dear "," dearest "," decent "," decimal "," decisive "," deep "," defenseless "," defensive "," defiant "," deficient "," definite "," definitive "," delayed "," delectable "," delicious "," delightful "," delirious "," demanding "," dense "," dental "," dependable "," dependent "," descriptive "," deserted "," detailed "," determined "," devoted "," different "," difficult "," digital "," diligent "," dim "," dimpled "," dimwitted "," direct "," disastrous "," discrete "," disfigured "," disgusting "," disloyal "," dismal "," distant "," downright "," dreary "," dirty "," disguised "," dishonest "," dismal "," distant "," distinct "," distorted "," dizzy "," dopey "," doting "," double "," downright "," drab "," drafty "," dramatic "," dreary "," droopy "," dry "," dual "," dull "," dutiful "," each "," eager "," earnest "," early "," easy "," easy-going "," ecstatic "," edible "," educated "," elaborate "," elastic "," elated "," elderly "," electric "," elegant "," elementary "," elliptical "," embarrassed "," embellished "," eminent "," emotional "," empty "," enchanted "," enchanting "," energetic "," enlightened "," enormous "," enraged "," entire "," envious "," equal "," equatorial "," essential "," esteemed "," ethical "," euphoric "," even "," evergreen "," everlasting "," every "," evil "," exalted "," excellent "," exemplary "," exhausted "," excitable "," excited "," exciting "," exotic "," expensive "," experienced "," expert "," extraneous "," extroverted "," extra-large "," extra-small "," fabulous "," failing "," faint "," fair "," faithful "," fake "," false "," familiar "," famous "," fancy "," fantastic "," far "," faraway "," far-flung "," far-off "," fast "," fat "," fatal "," fatherly "," favorable "," favorite "," fearful "," fearless "," feisty "," feline "," female "," feminine "," few "," fickle "," filthy "," fine "," finished "," firm "," first "," firsthand "," fitting "," fixed "," flaky "," flamboyant "," flashy "," flat "," flawed "," flawless "," flickering "," flimsy "," flippant "," flowery "," fluffy "," fluid "," flustered "," focused "," fond "," foolhardy "," foolish "," forceful "," forked "," formal "," forsaken "," forthright "," fortunate "," fragrant "," frail "," frank "," frayed "," free "," French "," fresh "," frequent "," friendly "," frightened "," frightening "," frigid "," frilly "," frizzy "," frivolous "," front "," frosty "," frozen "," frugal "," fruitful "," full "," fumbling "," functional "," funny "," fussy "," fuzzy "," gargantuan "," gaseous "," general "," generous "," gentle "," genuine "," giant "," giddy "," gigantic "," gifted "," giving "," glamorous "," glaring "," glass "," gleaming "," gleeful "," glistening "," glittering "," gloomy "," glorious "," glossy "," glum "," golden "," good "," good-natured "," gorgeous "," graceful "," gracious "," grand "," grandiose "," granular "," grateful "," grave "," gray "," great "," greedy "," green "," gregarious "," grim "," grimy "," gripping "," grizzled "," gross "," grotesque "," grouchy "," grounded "," growing "," growling "," grown "," grubby "," gruesome "," grumpy "," guilty "," gullible "," gummy "," hairy "," half "," handmade "," handsome "," handy "," happy "," happy-go-lucky "," hard "," hard-to-find "," harmful "," harmless "," harmonious "," harsh "," hasty "," hateful "," haunting "," healthy "," heartfelt "," hearty "," heavenly "," heavy "," hefty "," helpful "," helpless "," hidden "," hideous "," high "," high-level "," hilarious "," hoarse "," hollow "," homely "," honest "," honorable "," honored "," hopeful "," horrible "," hospitable "," hot "," huge "," humble "," humiliating "," humming "," humongous "," hungry "," hurtful "," husky "," icky "," icy "," ideal "," idealistic "," identical "," idle "," idiotic "," idolized "," ignorant "," ill "," illegal "," ill-fated "," ill-informed "," illiterate "," illustrious "," imaginary "," imaginative "," immaculate "," immaterial "," immediate "," immense "," impassioned "," impeccable "," impartial "," imperfect "," imperturbable "," impish "," impolite "," important "," impossible "," impractical "," impressionable "," impressive "," improbable "," impure "," inborn "," incomparable "," incompatible "," incomplete "," inconsequential "," incredible "," indelible "," inexperienced "," indolent "," infamous "," infantile "," infatuated "," inferior "," infinite "," informal "," innocent "," insecure "," insidious "," insignificant "," insistent "," instructive "," insubstantial "," intelligent "," intent "," intentional "," interesting "," internal "," international "," intrepid "," ironclad "," irresponsible "," irritating "," itchy "," jaded "," jagged "," jam-packed "," jaunty "," jealous "," jittery "," joint "," jolly "," jovial "," joyful "," joyous "," jubilant "," judicious "," juicy "," jumbo "," junior "," jumpy "," juvenile "," kaleidoscopic "," keen "," key "," kind "," kindhearted "," kindly "," klutzy "," knobby "," knotty "," knowledgeable "," knowing "," known "," kooky "," kosher "," lame "," lanky "," large "," last "," lasting "," late "," lavish "," lawful "," lazy "," leading "," lean "," leafy "," left "," legal "," legitimate "," light "," lighthearted "," likable "," likely "," limited "," limp "," limping "," linear "," lined "," liquid "," little "," live "," lively "," livid "," loathsome "," lone "," lonely "," long "," long-term "," loose "," lopsided "," lost "," loud "," lovable "," lovely "," loving "," low "," loyal "," lucky "," lumbering "," luminous "," lumpy "," lustrous "," luxurious "," mad "," made-up "," magnificent "," majestic "," major "," male "," mammoth "," married "," marvelous "," masculine "," massive "," mature "," meager "," mealy "," mean "," measly "," meaty "," medical "," mediocre "," medium "," meek "," mellow "," melodic "," memorable "," menacing "," merry "," messy "," metallic "," mild "," milky "," mindless "," miniature "," minor "," minty "," miserable "," miserly "," misguided "," misty "," mixed "," modern "," modest "," moist "," monstrous "," monthly "," monumental "," moral "," mortified "," motherly "," motionless "," mountainous "," muddy "," muffled "," multicolored "," mundane "," murky "," mushy "," musty "," muted "," mysterious "," naive "," narrow "," nasty "," natural "," naughty "," nautical "," near "," neat "," necessary "," needy "," negative "," neglected "," negligible "," neighboring "," nervous "," new "," next "," nice "," nifty "," nimble "," nippy "," nocturnal "," noisy "," nonstop "," normal "," notable "," noted "," noteworthy "," novel "," noxious "," numb "," nutritious "," nutty "," obedient "," obese "," oblong "," oily "," oblong "," obvious "," occasional "," odd "," oddball "," offbeat "," offensive "," official "," old "," old-fashioned "," only "," open "," optimal "," optimistic "," opulent "," orange "," orderly "," organic "," ornate "," ornery "," ordinary "," original "," other "," our "," outlying "," outgoing "," outlandish "," outrageous "," outstanding "," oval "," overcooked "," overdue "," overjoyed "," overlooked "," palatable "," pale "," paltry "," parallel "," parched "," partial "," passionate "," past "," pastel "," peaceful "," peppery "," perfect "," perfumed "," periodic "," perky "," personal "," pertinent "," pesky "," pessimistic "," petty "," phony "," physical "," piercing "," pink "," pitiful "," plain "," plaintive "," plastic "," playful "," pleasant "," pleased "," pleasing "," plump "," plush "," polished "," polite "," political "," pointed "," pointless "," poised "," poor "," popular "," portly "," posh "," positive "," possible "," potable "," powerful "," powerless "," practical "," precious "," present "," prestigious "," pretty "," precious "," previous "," pricey "," prickly "," primary "," prime "," pristine "," private "," prize "," probable "," productive "," profitable "," profuse "," proper "," proud "," prudent "," punctual "," pungent "," puny "," pure "," purple "," pushy "," putrid "," puzzled "," puzzling "," quaint "," qualified "," quarrelsome "," quarterly "," queasy "," querulous "," questionable "," quick "," quick-witted "," quiet "," quintessential "," quirky "," quixotic "," quizzical "," radiant "," ragged "," rapid "," rare "," rash "," raw "," recent "," reckless "," rectangular "," ready "," real "," realistic "," reasonable "," red "," reflecting "," regal "," regular "," reliable "," relieved "," remarkable "," remorseful "," remote "," repentant "," required "," respectful "," responsible "," repulsive "," revolving "," rewarding "," rich "," rigid "," right "," ringed "," ripe "," roasted "," robust "," rosy "," rotating "," rotten "," rough "," round "," rowdy "," royal "," rubbery "," rundown "," ruddy "," rude "," runny "," rural "," rusty "," sad "," safe "," salty "," same "," sandy "," sane "," sarcastic "," sardonic "," satisfied "," scaly "," scarce "," scared "," scary "," scented "," scholarly "," scientific "," scornful "," scratchy "," scrawny "," second "," secondary "," second-hand "," secret "," self-assured "," self-reliant "," selfish "," sentimental "," separate "," serene "," serious "," serpentine "," several "," severe "," shabby "," shadowy "," shady "," shallow "," shameful "," shameless "," sharp "," shimmering "," shiny "," shocked "," shocking "," shoddy "," short "," short-term "," showy "," shrill "," shy "," sick "," silent "," silky "," silly "," silver "," similar "," simple "," simplistic "," sinful "," single "," sizzling "," skeletal "," skinny "," sleepy "," slight "," slim "," slimy "," slippery "," slow "," slushy "," small "," smart "," smoggy "," smooth "," smug "," snappy "," snarling "," sneaky "," sniveling "," snoopy "," sociable "," soft "," soggy "," solid "," somber "," some "," spherical "," sophisticated "," sore "," sorrowful "," soulful "," soupy "," sour "," Spanish "," sparkling "," sparse "," specific "," spectacular "," speedy "," spicy "," spiffy "," spirited "," spiteful "," splendid "," spotless "," spotted "," spry "," square "," squeaky "," squiggly "," stable "," staid "," stained "," stale "," standard "," starchy "," stark "," starry "," steep "," sticky "," stiff "," stimulating "," stingy "," stormy "," straight "," strange "," steel "," strict "," strident "," striking "," striped "," strong "," studious "," stunning "," stupendous "," stupid "," sturdy "," stylish "," subdued "," submissive "," substantial "," subtle "," suburban "," sudden "," sugary "," sunny "," super "," superb "," superficial "," superior "," supportive "," sure-footed "," surprised "," suspicious "," svelte "," sweaty "," sweet "," sweltering "," swift "," sympathetic "," tall "," talkative "," tame "," tan "," tangible "," tart "," tasty "," tattered "," taut "," tedious "," teeming "," tempting "," tender "," tense "," tepid "," terrible "," terrific "," testy "," thankful "," that "," these "," thick "," thin "," third "," thirsty "," this "," thorough "," thorny "," those "," thoughtful "," threadbare "," thrifty "," thunderous "," tidy "," tight "," timely "," tinted "," tiny "," tired "," torn "," total "," tough "," traumatic "," treasured "," tremendous "," tragic "," trained "," tremendous "," triangular "," tricky "," trifling "," trim "," trivial "," troubled "," true "," trusting "," trustworthy "," trusty "," truthful "," tubby "," turbulent "," twin "," ugly "," ultimate "," unacceptable "," unaware "," uncomfortable "," uncommon "," unconscious "," understated "," unequaled "," uneven "," unfinished "," unfit "," unfolded "," unfortunate "," unhappy "," unhealthy "," uniform "," unimportant "," unique "," united "," unkempt "," unknown "," unlawful "," unlined "," unlucky "," unnatural "," unpleasant "," unrealistic "," unripe "," unruly "," unselfish "," unsightly "," unsteady "," unsung "," untidy "," untimely "," untried "," untrue "," unused "," unusual "," unwelcome "," unwieldy "," unwilling "," unwitting "," unwritten "," upbeat "," upright "," upset "," urban "," usable "," used "," useful "," useless "," utilized "," utter "," vacant "," vague "," vain "," valid "," valuable "," vapid "," variable "," vast "," velvety "," venerated "," vengeful "," verifiable "," vibrant "," vicious "," victorious "," vigilant "," vigorous "," villainous "," violet "," violent "," virtual "," virtuous "," visible "," vital "," vivacious "," vivid "," voluminous "," wan "," warlike "," warm "," warmhearted "," warped "," wary "," wasteful "," watchful "," waterlogged "," watery "," wavy "," wealthy "," weak "," weary "," webbed "," wee "," weekly "," weepy "," weighty "," weird "," welcome "," well-documented "," well-groomed "," well-informed "," well-lit "," well-made "," well-off "," well-to-do "," well-worn "," wet "," which "," whimsical "," whirlwind "," whispered "," white "," whole "," whopping "," wicked "," wide "," wide-eyed "," wiggly "," wild "," willing "," wilted "," winding "," windy "," winged "," wiry "," wise "," witty "," wobbly "," woeful "," wonderful "," wooden "," woozy "," wordy "," worldly "," worn "," worried "," worrisome "," worse "," worst "," worthless "," worthwhile "," worthy "," wrathful "," wretched "," writhing "," wrong "," wry "," yawning "," yearly "," yellow "," yellowish "," young "," youthful "," yummy "," zany "," zealous "," zesty "," zigzag "];
    $adverbs = [" aboard "," abnormally "," about "," abroad "," absentmindedly "," absolutely "," abundantly "," accidentally "," accordingly "," actively "," actually "," acutely "," admiringly "," affectionately "," affirmatively "," after "," afterwards "," agreeably "," almost "," already "," always "," amazingly "," angrily "," annoyingly "," annually "," anxiously "," anyhow "," anyplace "," anyway "," anywhere "," appreciably "," appropriately "," around "," arrogantly "," aside "," assuredly "," astonishingly "," away "," awfully "," awkwardly "," badly "," barely "," bashfully "," beautifully "," before "," begrudgingly "," believably "," bewilderedly "," bewilderingly "," bitterly "," bleakly "," blindly "," blissfully "," boldly "," boastfully "," boldly "," boyishly "," bravely "," briefly "," brightly "," brilliantly "," briskly "," brutally "," busily "," calmly "," candidly "," carefully "," carelessly "," casually "," cautiously "," certainly "," charmingly "," cheerfully "," chiefly "," childishly "," cleanly "," clearly "," cleverly "," closely "," cloudily "," clumsily "," coaxingly "," coincidentally "," coldly "," colorfully "," commonly "," comfortably "," compactly "," compassionately "," completely "," confusedly "," consequently "," considerably "," considerately "," consistently "," constantly "," continually "," continuously "," coolly "," correctly "," courageously "," covertly "," cowardly "," crazily "," crossly "," cruelly "," cunningly "," curiously "," currently "," customarily "," cutely "," daily "," daintily "," dangerously "," daringly "," darkly "," dastardly "," dearly "," decently "," deeply "," defiantly "," deftly "," deliberately "," delicately "," delightfully "," densely "," diagonally "," differently "," diligently "," dimly "," directly "," disorderly "," divisively "," docilely "," dopily "," doubtfully "," down "," dramatically "," dreamily "," during "," eagerly "," early "," earnestly "," easily "," efficiently "," effortlessly "," elaborately "," eloquently "," elegantly "," elsewhere "," emotionally "," endlessly "," energetically "," enjoyably "," enormously "," enough "," enthusiastically "," entirely "," equally "," especially "," essentially "," eternally "," ethically "," even "," evenly "," eventually "," evermore "," every "," everywhere "," evidently "," evocatively "," exactly "," exceedingly "," exceptionally "," excitedly "," exclusively "," explicitly "," expressly "," extensively "," externally "," extra "," extraordinarily "," extremely "," fairly "," faithfully "," famously "," far "," fashionably "," fast "," fatally "," favorably "," ferociously "," fervently "," fiercely "," fiery "," finally "," financially "," finitely "," fluently "," fondly "," foolishly "," forever "," formally "," formerly "," fortunately "," forward "," frankly "," frantically "," freely "," frequently "," frenetically "," fully "," furiously "," furthermore "," generally "," generously "," genuinely "," gently "," genuinely "," girlishly "," gladly "," gleefully "," gracefully "," graciously "," gradually "," gratefully "," greatly "," greedily "," grimly "," grudgingly "," habitually "," half-heartedly "," handily "," handsomely "," haphazardly "," happily "," hastily "," harmoniously "," harshly "," hastily "," hatefully "," hauntingly "," healthily "," heartily "," heavily "," helpfully "," hence "," highly "," hitherto "," honestly "," hopelessly "," horizontally "," hourly "," how "," however "," hugely "," humorously "," hungrily "," hurriedly "," hysterically "," icily "," identifiably "," idiotically "," imaginatively "," immeasurably "," immediately "," immensely "," impatiently "," impressively "," inappropriately "," incessantly "," incorrectly "," indeed "," independently "," indoors "," indubitably "," inevitably "," infinitely "," informally "," infrequently "," innocently "," inquisitively "," instantly "," intelligently "," intensely "," intently "," interestingly "," intermittently "," internally "," invariably "," invisibly "," inwardly "," ironically "," irrefutably "," irritably "," jaggedly "," jauntily "," jealously "," jovially "," joyfully "," joylessly "," joyously "," jubilantly "," judgmentally "," just "," justly "," keenly "," kiddingly "," kindheartedly "," kindly "," knavishly "," knottily "," knowingly "," knowledgeably "," kookily "," lastly "," late "," lately "," later "," lazily "," less "," lightly "," likely "," limply "," lithely "," lively "," loftily "," longingly "," loosely "," loudly "," lovingly "," loyally "," luckily "," luxuriously "," madly "," magically "," mainly "," majestically "," markedly "," materially "," meaningfully "," meanly "," meantime "," meanwhile "," measurably "," mechanically "," medically "," menacingly "," merely "," merrily "," methodically "," mightily "," miserably "," mockingly "," monthly "," morally "," more "," moreover "," mortally "," mostly "," much "," mysteriously "," nastily "," naturally "," naughtily "," nearby "," nearly "," neatly "," needily "," negatively "," nervously "," never "," nevertheless "," next "," nicely "," nightly "," noisily "," normally "," nosily "," not "," now "," nowadays "," numbly "," obediently "," obligingly "," obnoxiously "," obviously "," occasionally "," oddly "," offensively "," officially "," often "," ominously "," once "," only "," openly "," optimistically "," orderly "," ordinarily "," outdoors "," outrageously "," outwardly "," outwards "," overconfidently "," overseas "," painfully "," painlessly "," paradoxically "," partially "," particularly "," passionately "," patiently "," perfectly "," periodically "," perpetually "," persistently "," personally "," persuasively "," physically "," plainly "," playfully "," poetically "," poignantly "," politely "," poorly "," positively "," possibly "," potentially "," powerfully "," presently "," presumably "," prettily "," previously "," primly "," principally "," probably "," promptly "," properly "," proudly "," punctually "," puzzlingly "," quaintly "," queasily "," questionably "," questioningly "," quicker "," quickly "," quietly "," quirkily "," quite "," quizzically "," randomly "," rapidly "," rarely "," readily "," really "," reasonably "," reassuringly "," recently "," recklessly "," regularly "," reliably "," reluctantly "," remarkably "," repeatedly "," reproachfully "," reponsibly "," resentfully "," respectably "," respectfully "," restfully "," richly "," ridiculously "," righteously "," rightfully "," rightly "," rigidly "," roughly "," routinely "," rudely "," ruthlessly "," sadly "," safely "," scarcely "," scarily "," scientifically "," searchingly "," secretively "," securely "," sedately "," seemingly "," seldom "," selfishly "," selflessly "," separately "," seriously "," shakily "," shamelessly "," sharply "," sheepishly "," shoddily "," shortly "," shrilly "," significantly "," silently "," simply "," sincerely "," singularly "," shyly "," skillfully "," sleepily "," slightly "," slowly "," slyly "," smoothly "," so "," softly "," solely "," solemnly "," solidly "," silicitiously "," somehow "," sometimes "," somewhat "," somewhere "," soon "," specially "," specifically "," spectacularly "," speedily "," spiritually "," splendidly "," sporadically "," spasmodically "," startlingly "," steadily "," stealthily "," sternly "," still "," strenuously "," stressfully "," strictly "," structurally "," studiously "," stupidly "," stylishly "," subsequently "," substantially "," subtly "," successfully "," suddenly "," sufficiently "," suitably "," superficially "," supremely "," surely "," surprisingly "," suspiciously "," sweetly "," swiftly "," sympathetically "," systematically "," temporarily "," tenderly "," tensely "," tepidly "," terribly "," thankfully "," then "," there "," thereby "," thoroughly "," thoughtfully "," thus "," tightly "," today "," together "," tomorrow "," too "," totally "," touchingly "," tremendously "," truly "," truthfully "," twice "," ultimately "," unabashedly "," unanimously "," unbearably "," unbelievably "," unemotionally "," unethically "," unexpectedly "," unfailingly "," unfavorably "," unfortunately "," uniformly "," unilaterally "," unimpressively "," universally "," unnaturally "," unnecessarily "," unquestionably "," unwillingly "," up "," upbeat "," unkindly "," upliftingly "," upright "," unselfishly "," upside-down "," unskillfully "," upward "," upwardly "," urgently "," usefully "," uselessly "," usually "," utterly "," vacantly "," vaguely "," vainly "," valiantly "," vastly "," verbally "," vertically "," very "," viciously "," victoriously "," vigilantly "," vigorously "," violently "," visibly "," visually "," vivaciously "," voluntarily "," warmly "," weakly "," wearily "," weekly "," well "," wetly "," when "," where "," while "," whole-heartedly "," wholly "," why "," wickedly "," widely "," wiggly "," wildly "," willfully "," willingly "," wisely "," woefully "," wonderfully "," worriedly "," worthily "," wrongly "," yearly "," yearningly "," yesterday "," yet "," youthfully "," zanily "," zealously "," zestfully "," zestily "];
    $waste = array_merge($waste, $adjectives, $adverbs);
    foreach($waste as $word){
        $clean = str_replace($word, " ______ ", $clean);
    }
    return $clean;
}

