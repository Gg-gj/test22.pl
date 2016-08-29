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

my $path2;
sub DBI{
  while(my $string = <FILE>){
    if($string =~ /-Ddomain.home;(.*?);/){
      $path2 = $1."/config/config.xml";
    }
}
}
DBI();
print $string;

print path2;

open FILE2, $path2 or die;

sub Call{ #подпрограмма для обработки данных
   $new = sprintf("%.0f", $1/4); #+1)*16; вычисление и округление
   if($new<$new_call){ #определяем, меньше ли $new минимального значения
  	$new = $new_call; #если меньше, то мы присвоем ему минимальное
  	#print "$new\n"
    }
   $r[$i2] =~ s/$1/$new/; #заменяем значение глобальной переменной
};

sub Call2{ #функция, которая проверяет строку, если какая-то переменная выражена в g, то мы переводим её в m

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
sub Call3{ #функция, которая переводит гигабайты в мегабайты
	$new = sprintf("%.0f", $1*1024/4); #переводим гигабайты в мегабайты
	if($new<$new_call){ #определяем, меньше ли $new минимального значения
  	$new = $new_call; #если меньше, то мы присвоем ему минимальное
    }
	$r[$i2] =~s/$xm$1(g)/$xm$new()m/g; #меняем данные в масиве
	$r[$i2] =~s/$xm=$1(g)/$xm=$new()m/g; #меняем данные в масиве
	
}


$path = dirname($0);
unless(open FILE3, '>', "$path/config.xml.bak"){ 
    die "can't create or open bak file";}

unless(open FILE4, '>', "$path2.bak"){ 
    die "can't create or open bak file";}

while(my $z = <FILE2>){
push my @r2, $z;
print FILE3 @r2 or die "Can't write to bak file";
print FILE4 @r2 or die "Can't write to bak file";
}
close FILE3 or die "can't close bak file";
close FILE4 or die "can't close bak file";


open FILE2, $path2 or die;

while($_ = <FILE2>){ #цикл с перебором файла посторочно
push @r, $_; #помецаем строки в масив
++$i3; #прибавляем +1 к индефикатору количества элементов в массиве
  if($i3 > 2){ #если в масиве больше 2 элементов, то мы начинаем его проверять
   if($r[$i] =~/<name>(.*?)<\/name>/, $r[$i2] =~/<arguments>-Xms(\d+). -Xmx(\d+). -XX/){ #если мы находим нужные элементы, то мы стартуем условие if
      if($r[$i2] =~ /(\d+)g/){ #ищем число выраженное в g
       Call2(); #запускаем подпрограмму
      }
       
       $new_call = 1024; #задаём минимальное значение
       $r[$i2] =~ /-Xms(\d+)m/; #даём значение для переменной $1
       Call(); #запускаем подпрограмму
       
       $new_call = 4096; #задаём минимальное значение
       $r[$i2] =~ /-Xmx(\d+)m/; #даём значение переменной $1
       Call(); #запускаем подпрограмму
       
       $new_call = 512; #задаём минимальное значение
       $r[$i2] =~ /-XX:PermSize=(\d+)m/; #даём значение переменной $1
       Call(); #запускаем подпрограмму
       
       $new_call = 1024; #задаём минимальное значение
       $r[$i2] =~ /-XX:MaxPermSize=(\d+)m/; #даём значение переменной $1
       Call(); #запускаем подпрограмму

print $r[$i]; 
print $r[$i2];
print "\n";
}
++$i; #+1 к индефикатору 1 элемента массива
++$i2; #+1 к индефикатору элемента, который находится ниже другого элемента с индефикатором ++$i
}
}
close FILE or die;

#open FILE5, '>', $path2 or die "Can't open file config.xml: $!"; #открываем файл config.xml
#print FILE5 @r; #записываем в файл данные
#close FILE5; #закрываем файл


print 'DONE';