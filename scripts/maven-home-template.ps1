# 修改环境变量添加maven执行文件路径
$env:MAVEN_HOME="D:\develop\server\apache-maven-3.8.7"
$env:Path=$env:MAVEN_HOME+"\bin;"+$env:Path
