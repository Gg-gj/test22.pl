use strict;
use warnings;
use 5.008;
use autodie;
use XML::LibXML;
use Data::Dumper;
use FileHandle;

my @numArgs = @ARGV;
print "Runing $0\n";

my $file1 = $numArgs[0]; #logback_s.xml
my $file2 = $numArgs[1]; #logback.xml
my $file3 = $numArgs[1] . ".bak"; #резервная копия $file2

print "first file is $file1\n";
print "second file is $file2\n";


my $parser = XML::LibXML->new();

#блок с чтением информации из $file1
open my $fh, '<', "$file1" or die "can't open $file1";
my @fragments = map {
   chomp;
   $parser->parse_balanced_chunk($_);
} <$fh>;
close $fh;

my $xml = $parser->load_xml(location => "$file2") or die "can't open $file2"; #открываем $file1

#блок с созданием\записью резервной копии $file3
my $FILE;
unless(open $FILE, '>', $file3){
	die  "can't create or open $file3"; 
};
print $FILE "$xml"; #записываем в $file3 текст из $file2
close $FILE; #закрываем FILE

#блок с заменой тэга pattern
my @or_nodes = $xml->findnodes('//encoder');
$or_nodes[0]->removeChildNodes;
$or_nodes[0]->appendChild($_) for @fragments;

#блок с добавлением jmxConfigurator (пока что только в конец...)
my $trace2 = $xml->findnodes("configuration")->[0]->exists("jmxConfigurator"); #путь к тэгу
my $false = $xml->exists("$trace2"); #проверяем присуствие этого тэга в $file2
if ($false == 0){                    #если тэга </jmxConfigurator> нет, то мы добавляем его в $file1
my $trace = $xml->findnodes("configuration")->[0]; #путь к месту вставки тэга
my $new_string = XML::LibXML::Element->new("jmxConfigurator"); # обращение к библиотеке XML::LibXML::Element
my $test = $trace->findnodes("configuration"); #путь к <configuration>
$trace->insertBefore($new_string, $test); #добавлемя тэг </jmxConfigurator>
}

#блок с атрибутами
my $new =  XML::LibXML::Element->new( $xml ); #вызов библиотеки XML::LibXML::Element
my $aname = $xml->findnodes('configuration')->[0]; #путь к тэгу, которому мы будем добавлять атрибуты
$aname->setAttribute('scan', 'true'); #добавляем атрибут "scan"
$aname->setAttribute('scanPeriod', '20 seconds'); #добавляем атрибут "scanPeriod"

#блок с записью в файл
print $xml->toString(1);
my $xml2 = $xml;
$xml2->toFile($file2) or die "can't wtite to $file2";

#блок с операциями изменения входных данных и записью этих данных в файл в add_logger.py
my $inf_file = $xml->findnodes("configuration")->[0]->findnodes("appender")->[0]->findnodes("file"); #берём информацию из тэга <file>
#Get logback variable from ps

my $file4 = 'C:\gleb\eclipse\ParseXMLPerl\add_logger.py'; #путь к add_logger.py
open my $info, '<',  "$file4" or die "can't open $file4"; #cчитываем данные из add_logger.py
my $inf;                                                  #эта скалярная переменая для считывания данных из файла add_logger.py и запись их в массив @writer
my @writer;                                               #массив, который будет записывать в себя строки add_logger.py
my $i = 0;                                                #счётчик, который будет определять элементы массива @writer
while( $inf = <$info>){                                   #начало процесса, который будет считывать и записывать данные в add_loger.py
	print "$inf \n";                                      #выввод строк
	push  @writer, $inf;                                  #добавляем строки в массив @writer
	$writer[$i] =~ s/\/oracle\/logs\/ufos\/server.log/$inf_file/gi; #ищем то, что надо заменить на содержимое тэга <file>
	open FILE, '>', $file4;                               #добавляем "FILE", через который мы будем добавлять сроки массива @writer в add_logger.py
	print FILE @writer;                                   #запись данных в add_loger.py
	++$i;                                                 #+1 к переменной, которая определяет элемент массива @writer
};
__END__