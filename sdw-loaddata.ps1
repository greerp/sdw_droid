. "$PSScriptRoot\sdw-functions.ps1"

$root = "C:\Users\greepau\AndroidStudioProjects\sdw_droid"

$handicaps = (0, 30, 60, 90, 120, 150, 180, 240, 270, 300)
$courses =  get-Courses $root
$users = get-Users $root
$events = get-Events $root
$series = get-Series $root

#shuffle-list $users |format-Table
#$users|format-table


$entryid=0
foreach ( $event in $events ) {
    


    $entry = New-EventEntry

    $entry.id = ++$entryid
    $entry.eventid = $event.id

    



    $r = RandomIntBetween 1 10
    #Write-Host $r
}












