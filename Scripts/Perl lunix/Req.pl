use File::Basename;

my @r;
my $string;

open INF, "hostname -f|" or die; #read current host name
my $inf = <INF>; #write current host name in the scalar
chomp($inf); #cut /n in the $inf
close INF or die; #close INF, e.i. command "hostname -f|";
my $path = dirname($0);
$path = "$path/$inf.txt";
open FILE, "$path" or die "Can't open a file $path: $!";

my $path2; #���������� ��� �������� ���� � config.xml
sub DBI{ #������������ ��� ���������� ������ �� �����
  while(my $string = <FILE>){
    if($string =~ /-Ddomain.home;(.*?);/){ #���� ������ ��������������� config.xml
      $path2 = $1."/config/config.xml"; #��������� ������
    }
}
}
DBI();

open FILE2, $path2 or die; #��������� config.xml

sub Call{ #������������ ��� ��������� ������
   $new = sprintf("%.0f", $1/4); #+1)*16; ���������� � ����������
   if($new<$new_call){ #����������, ������ �� $new ������������ ��������
  	$new = $new_call; #���� ������, �� �� �������� ��� �����������
  	#print "$new\n"
    }
   $r[$i2] =~ s/$1/$new/; #�������� �������� ���������� ����������
};

sub Call2{ #�������, ������� ��������� ������, ���� �����-�� ���������� �������� � g, �� �� ��������� � � m

	$new_call = 1024;
	if($r[$i2] =~ /-Xms(\d+)g/){
	$xm = "Xms";
	Call3();
	}
	
	$new_call = 4096;
	if($r[$i2] =~ /-Xmx(\d+)g/){
        $xm = "Xmx";
        Call3();
	}
	
	$new_call = 512;
	if($r[$i2] =~ /-XX:PermSize=(\d+)g/){
	$xm = "XX:PermSize";
	Call3();
	}
	
	$new_call = 1024;
	if($r[$i2] =~ /-XX:MaxPermSize=(\d+)g/){
	$xm = "XX:MaxPermSize";
	Call3();
	}
	$r[$i2] =~ s/\(\)m/m/g;
} 
sub Call3{ #�������, ������� ��������� ��������� � ���������
	$new = sprintf("%.0f", $1*1024/4); #��������� ��������� � ���������
	if($new<$new_call){ #����������, ������ �� $new ������������ ��������
  	$new = $new_call; #���� ������, �� �� �������� ��� �����������
    }
	$r[$i2] =~s/$xm$1(g)/$xm$new()m/g; #������ ������ � ������
	$r[$i2] =~s/$xm=$1(g)/$xm=$new()m/g; #������ ������ � ������
	
}


$path = dirname($0); #������� ����������
unless(open FILE3, '>', "$path/config.xml.bak"){ #����, ��� �� �������� ����� ���� � ����� ���������� ������� ��� ������� ������������ ����
    die "can't create or open bak file";}

unless(open FILE4, '>', "$path2.bak"){ #����, ��� �� �������� ����� ���� � ����� config.xml ��� ������� ������������ ����
    die "can't create or open bak file";}

while(my $z = <FILE2>){ #����, ��� �� ��������� ����� �� config.xml � ���������� �� � ����� �����
push my @r2, $z;
print FILE3 @r2 or die "Can't write to bak file"; #���������� ������� ������ � ����� ����
print FILE4 @r2 or die "Can't write to bak file"; #���������� ������� ������ � ����� ����
}
close FILE3 or die "can't close bak file"; #��������� ����� ����
close FILE4 or die "can't close bak file"; #��������� ����� ����


open FILE2, $path2 or die;

while($_ = <FILE2>){ #���� � ��������� ����� ����������
push @r, $_; #�������� ������ � �����
++$i3; #���������� +1 � ������������ ���������� ��������� � �������
  if($i3 > 2){ #���� � ������ ������ 2 ���������, �� �� �������� ��� ���������
   if($r[$i] =~/<name>(.*?)<\/name>/, $r[$i2] =~/<arguments>-Xms(\d+). -Xmx(\d+). -XX/){ #���� �� ������� ������ ��������, �� �� �������� ������� if
      if($r[$i2] =~ /(\d+)g/){ #���� ����� ���������� � g
       Call2(); #��������� ������������
      }
       
       $new_call = 1024; #����� ����������� ��������
       $r[$i2] =~ /-Xms(\d+)m/; #��� �������� ��� ���������� $1
       Call(); #��������� ������������
       
       $new_call = 4096; #����� ����������� ��������
       $r[$i2] =~ /-Xmx(\d+)m/; #��� �������� ���������� $1
       Call(); #��������� ������������
       
       $new_call = 512; #����� ����������� ��������
       $r[$i2] =~ /-XX:PermSize=(\d+)m/; #��� �������� ���������� $1
       Call(); #��������� ������������
       
       $new_call = 1024; #����� ����������� ��������
       $r[$i2] =~ /-XX:MaxPermSize=(\d+)m/; #��� �������� ���������� $1
       Call(); #��������� ������������

#print $r[$i]; 
#print $r[$i2]; #��� ��� ������ ����� ���������� ��������� ����������, �� ������ ������ ��� ���������������
#print "\n";
}
++$i; #+1 � ������������ 1 �������� �������
++$i2; #+1 � ������������ ��������, ������� ��������� ���� ������� �������� � ������������� ++$i
}
}
close FILE or die; #��������� ����

open FILE5, '>', $path2 or die "Can't open file config.xml: $!"; #��������� ���� config.xml
print FILE5 @r; #���������� � ���� ������
close FILE5; #��������� ����


print 'DONE';