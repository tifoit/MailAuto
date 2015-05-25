在命令行下执行：
java -jar mailSender_r.jar 邮件标题  邮件正文 附件1(要使用绝对路径,例如:c:\u.txt)
注意: 如果有多于1个附件的场合，通过在config.properties中修改attach.files=c:/a.txt,c:/b.txt来添加;

另外，
如果想把导出的mailSender.jar来作为通用的邮件发送功能来使用的话，
在部署mailSender.jar的同路径下，也需要人工手动拷贝一份config文件及其内容。

注意：
1.如果想导出jar，用来放到别的工程部署路径中来用，选择 export -> Jar file ，例如导出mailSender.jar
2.如果想导出直接用来执行的jar，用来直接部署到linux下直接来使用，选择 export -> Runnable Jar file ，例如导出mailSender_r.jar
