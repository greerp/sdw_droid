#gci 'F:\Android Icons' -Recurse -Include "ic_action_user*"|`%{ Write-Host $_.DirectoryName.Split("\\")[-1] }

param(
    [parameter(Mandatory=$true)][string] $sourcePath,
    [parameter(Mandatory=$true)][string] $fileSpec,
    [parameter(Mandatory=$true)][string] $destPath
)

$files = gci $sourcePath -Recurse -Include $fileSpec
foreach ( $f in $files){
    $pathPart = $f.DirectoryName.Split("\\")[-1];

    $destination = "$destPath\$pathPart"
    if ( Test-Path -Path $destination ){
        Copy-Item $f $destination
    }
}

