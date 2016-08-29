use File::Basename; #dirname()
open PS, 'ps -eo args|grep -i weblogic.name|set -eo pipefail|' or die "use the command of terminal 'ps -eo args|grep -i weblogic.name|set -o pipefail|' failed..."; #read global variables


my @t; #here will be saved changed output
my $e; #here will be changed output
my $oj; #here will be Dweblogic.Name
my $dir = dirname($0); #path to current dir

open INF, "hostname -f|" or die; #read current host name
my $inf = <INF>; #write current host name in the scalar
chomp($inf); #cut /n in the $inf
my $inf2 = "$dir/$inf"; #path to file
close INF or die; #close INF, e.i. command "hostname -f|";


while ($_ = <PS>){ #start circle while, which reading file line by line
$_ =~ s/ps -eo args\|grep -i weblogic.name\||args\|grep//;  #cut unusable data
$_ =~ s/ps //; #cut unusable data
$_ =~ s/-i //; #cut unusable data
$_ =~ s/-eo //; #cut unusable data
$_ =~ s/-c //; #cut unusable data
$_ =~ s/=/;/g; #replace "=" ";"
$_ =~ s/-Xmx/-Xmx;/g; #replace "-Xmx" "-Xmx;"
$_ =~ s/-Xms/-Xms;/g; #replace "-Xms" "-Xms;"
$_ =~ s/\s/; \n/g; #replace "\s" ";" \n"
if($_ =~ /-Dweblogic.Name;(.*?);/g){ #search weblogic.name...
  $oj = $1; #write to scalar variable of Dweblogic.Name
};

$_ =~ s/-/$inf;$oj;-/g; #replace "-" "server_name;weblogic.name;-"
if($_ !~ /^(.*?);(.*?);-(.*?);/){ #cut unusable data
$_ =~ s/^(.*?)\/(.*?); //; #cut unusable data
$_ =~ s/^\/(.*?)\/(.*?); //; #cut unusable data
$_ =~ s/^(.*?);(.*?);- //; #cut unusable data
$_ =~ s/weblogic\.Server; //; #cut unusable data
$_ =~ s/weblogic\.name; //; #cut unusable data
$_ =~ s/^(.*?); //; #cut unusable data
$_ =~ s/^-(.*?); //; #cut unusable data
}

$_ =~ s/^. \n/-/m; #replace trash string '-'
$_ =~ s/^\n/-/m; #replace empty strings '-'
$_ =~ s/^ \n/-/m; #replace empty strings '-'
$_ =~ s/^-//m; #cut unusable data
$_ =~ s/^-\n//gm; #cut unusable data

$e = $_; #our output
push @t, $e; #our output
}

unless(open FILE, '>', "$inf2.txt"){ #create\open file
	die  "can't create or open file $inf.txt"; #if this operation failed, programm just die
};

print FILE @t or die "Can't close $inf2.txt!"; #write changed output in the file
close FILE or die; #close file

print "done\n"; #print 'done'

__END__