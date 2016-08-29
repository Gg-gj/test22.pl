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
my $file3 = $numArgs[1] . ".bak"; #��������� ����� $file2

print "first file is $file1\n";
print "second file is $file2\n";


my $parser = XML::LibXML->new();

#���� � ������� ���������� �� $file1
open my $fh, '<', "$file1" or die "can't open $file1";
my @fragments = map {
   chomp;
   $parser->parse_balanced_chunk($_);
} <$fh>;
close $fh;

my $xml = $parser->load_xml(location => "$file2") or die "can't open $file2"; #��������� $file1

#���� � ���������\������� ��������� ����� $file3
my $FILE;
unless(open $FILE, '>', $file3){
	die  "can't create or open $file3"; 
};
print $FILE "$xml"; #���������� � $file3 ����� �� $file2
close $FILE; #��������� FILE

#���� � ������� ���� pattern
my @or_nodes = $xml->findnodes('//encoder');
$or_nodes[0]->removeChildNodes;
$or_nodes[0]->appendChild($_) for @fragments;

#���� � ����������� jmxConfigurator (���� ��� ������ � �����...)
my $trace2 = $xml->findnodes("configuration")->[0]->exists("jmxConfigurator"); #���� � ����
my $false = $xml->exists("$trace2"); #��������� ���������� ����� ���� � $file2
if ($false == 0){                    #���� ���� </jmxConfigurator> ���, �� �� ��������� ��� � $file1
my $trace = $xml->findnodes("configuration")->[0]; #���� � ����� ������� ����
my $new_string = XML::LibXML::Element->new("jmxConfigurator"); # ��������� � ���������� XML::LibXML::Element
my $test = $trace->findnodes("configuration"); #���� � <configuration>
$trace->insertBefore($new_string, $test); #��������� ��� </jmxConfigurator>
}

#���� � ����������
my $new =  XML::LibXML::Element->new( $xml ); #����� ���������� XML::LibXML::Element
my $aname = $xml->findnodes('configuration')->[0]; #���� � ����, �������� �� ����� ��������� ��������
$aname->setAttribute('scan', 'true'); #��������� ������� "scan"
$aname->setAttribute('scanPeriod', '20 seconds'); #��������� ������� "scanPeriod"

#���� � ������� � ����
print $xml->toString(1);
my $xml2 = $xml;
$xml2->toFile($file2) or die "can't wtite to $file2";

#���� � ���������� ��������� ������� ������ � ������� ���� ������ � ���� � add_logger.py
my $inf_file = $xml->findnodes("configuration")->[0]->findnodes("appender")->[0]->findnodes("file"); #���� ���������� �� ���� <file>
#Get logback variable from ps

my $file4 = 'C:\gleb\eclipse\ParseXMLPerl\add_logger.py'; #���� � add_logger.py
open my $info, '<',  "$file4" or die "can't open $file4"; #c�������� ������ �� add_logger.py
my $inf;                                                  #��� ��������� ��������� ��� ���������� ������ �� ����� add_logger.py � ������ �� � ������ @writer
my @writer;                                               #������, ������� ����� ���������� � ���� ������ add_logger.py
my $i = 0;                                                #�������, ������� ����� ���������� �������� ������� @writer
while( $inf = <$info>){                                   #������ ��������, ������� ����� ��������� � ���������� ������ � add_loger.py
	print "$inf \n";                                      #������ �����
	push  @writer, $inf;                                  #��������� ������ � ������ @writer
	$writer[$i] =~ s/\/oracle\/logs\/ufos\/server.log/$inf_file/gi; #���� ��, ��� ���� �������� �� ���������� ���� <file>
	open FILE, '>', $file4;                               #��������� "FILE", ����� ������� �� ����� ��������� ����� ������� @writer � add_logger.py
	print FILE @writer;                                   #������ ������ � add_loger.py
	++$i;                                                 #+1 � ����������, ������� ���������� ������� ������� @writer
};
__END__