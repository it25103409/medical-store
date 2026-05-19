$staticDir = "c:\Users\HEALTHY MACHINES\Desktop\New folder (2)\src\main\resources\static"
$files = Get-ChildItem -Path $staticDir -Recurse -Filter "*.html"

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw -Encoding UTF8
    # Replace pure white backgrounds with soft grey
    $content = $content -replace 'bg-slate-50', 'bg-gray-100'
    $content = $content -replace 'class="([^"]*)\bbg-white\b([^"]*)"', 'class="$1bg-gray-50$2"'
    # Darken navbar & card whites
    $content = $content -replace 'bg-white/80', 'bg-gray-100/90'
    $content = $content -replace 'bg-white/90', 'bg-gray-100/90'
    Set-Content -Path $file.FullName -Value $content -Encoding UTF8
    Write-Host "Updated: $($file.Name)"
}
Write-Host "Done!"
