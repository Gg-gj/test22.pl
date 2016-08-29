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

my $path2; #here will be path to config.xml
sub DBI{ #function for reding data from file
  while(my $string = <FILE>){
    if($string =~ /-Ddomain.home;(.*?);/){ #search path to config.xml
      $path2 = $1."/config/config.xml"; #saving adress
    }
}
}
DBI();

open FILE2, $path2 or die; #open config.xml

sub Call{ #function for changing data
   $new = sprintf("%.0f", $1/4); #+1)*16; calculation and rounding
   if($new<$new_call){ #cheсking, smaller or bigger $new minimal value
  	$new = $new_call; #если меньше, то мы присвоем ему минимальное
    }
   $r[$i2] =~ s/$1/$new/; #change global variable
};

sub Call2{ #function, which cheсking string, if any variable expressed in gigabyte, we're will be translate it to megabyte

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
sub Call3{ #function, which translate gigabyte to megabytes
	$new = sprintf("%.0f", $1*1024/4); #translate gigabyte to megabytes
	if($new<$new_call){ #cheсking, smaller $new minimal value
  	$new = $new_call; #если меньше, то мы присвоем ему минимальное
    }
	$r[$i2] =~s/$xm$1(g)/$xm$new()m/g; #redacting array
	$r[$i2] =~s/$xm=$1(g)/$xm=$new()m/g; #redacting array
	
}


$path = dirname($0); #текущая директория
unless(open FILE3, '>', "$path/config.xml.bak"){ #цикл, где мы создадим бэкап файл в папке выполнения скрипта или откроем существующий файл
    die "can't create or open bak file";}

unless(open FILE4, '>', "$path2.bak"){ #цикл, где мы создадим бэкап файл в папке config.xml или откроем существующий файл
    die "can't create or open bak file";}

while(my $z = <FILE2>){ #цикл, где мы считываем текст из config.xml и записываем их в бэкап файлы
push my @r2, $z;
print FILE3 @r2 or die "Can't write to bak file"; #write current text of file in the bak file
print FILE4 @r2 or die "Can't write to bak file"; #write current text of file in the bak file
}
close FILE3 or die "can't close bak file"; #close bak file
close FILE4 or die "can't close bak file"; #close bak file


open FILE2, $path2 or die;

while($_ = <FILE2>){ #цикл с перебором файла посторочно
push @r, $_; #adding strings to array
++$i3; #adding +1 to indefecator количества элементов in the array
  if($i3 > 2){ #если в масиве больше 2 элементов, то мы начинаем его проверять
   if($r[$i] =~/<name>(.*?)<\/name>/, $r[$i2] =~/<arguments>-Xms(\d+). -Xmx(\d+). -XX/){ #если мы находим нужные элементы, то мы стартуем условие if
      if($r[$i2] =~ /(\d+)g/){ #ищем число выраженное в g
       Call2(); #starting function
      }
       
       $new_call = 1024; #задаём минимальное значение
       $r[$i2] =~ /-Xms(\d+)m/; #даём значение для переменной $1
       Call(); #starting function
       
       $new_call = 4096; #задаём минимальное значение
       $r[$i2] =~ /-Xmx(\d+)m/; #даём значение переменной $1
       Call(); #starting function
       
       $new_call = 512; #задаём минимальное значение
       $r[$i2] =~ /-XX:PermSize=(\d+)m/; #даём значение переменной $1
       Call(); #starting function
       
       $new_call = 1024; #задаём минимальное значение
       $r[$i2] =~ /-XX:MaxPermSize=(\d+)m/; #даём значение переменной $1
       Call(); #starting function

#print $r[$i]; 
#print $r[$i2]; #these strings show result of calculation
#print "\n";
}
++$i; #adding 1 to indeficator of 1st element of array
++$i2; #adding 1 to indeficator of element, which находится ниже another element with indeficator $i
}
}
close FILE or die; #close file

open FILE5, '>', $path2 or die "Can't open file config.xml: $!"; #open file config.xml
print FILE5 @r; #writing to the file
close FILE5; #close file


print 'DONE';