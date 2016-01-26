function get-Courses($basePath){
    Import-Csv "${basePath}\courses.csv"
}


function get-Entries($basePath){
    Import-Csv "${basePath}\entries.csv"
}

function get-Events($basePath){
    Import-Csv "${basePath}\events.csv"
}

function get-Series($basePath){
    Import-Csv "${basePath}\series.csv"
}

function get-Users($basePath){
    Import-Csv "${basePath}\Users.csv"
}


function RandomIntBetween([int]$low, [int]$high) {
    return Get-Random -Minimum $low -Maximum $high
}

function shuffle-list($list){
    $count = $list.Count
    $result = $list.Clone()

    for($i=0; $i -lt $count; $i++){
        $rnd = (Get-Random -Minimum 1 -Maximum 10)/10
        $j = [int]((($count-1) - $i) * $rnd) + $i
        $temp = $result[$i]
        $result[$i]=$result[$j]
        $result[$j]=$temp
    }
    return $result
}


function New-EventEntry{
    $properties = @{
        'id'="";
        'date'="";
        'eventid'="";
        'courseId'="";
    }
    $event = New-Object –TypeName PSObject –Prop $properties
    return $event
}


