use DBI;
use File::Basename;
use warnings;

$user = 'sysman'; #login (username)
$password = 'manager1'; #password
$source = 'dbi:Oracle:host=172.16.95.182;sid=orcl;port=1522'; #name of server
my $dbh=DBI->connect(($source),$user,$password) or die $!; #conecting to the server
my $servername; #hostname
my $weblogicname; #wls_name
my $param_name; #param_name
my $value; #param_value
my $file; #file

my @dots;
$some_dir = dirname($0); #current dir
opendir(DIR, $some_dir) or die $!; #opening dir
@dots = grep { /_wlsvar\.txt$/ && -f  "$some_dir/$_" } readdir(DIR); #list of êequired files


$dbh->do('TRUNCATE TABLE inv_wls') or warn "Can't clean table!"; #clean table

$dbh->prepare('INSERT INTO inv_wls (hostname, wls_name, param_name, param_value) VALUES (?, ?, ?, ?)'); #prepare for operation




foreach $file (@dots){ #each file in the current dir we're opening, read and write redacet output in the table
	open PS, $file; #open file
while(my $e =<PS>){ #circle while for adding element in the table

	if($e =~ /^(.*?);(.*?);(.*?);(.*?);/s){ #search string 'hostname;wls_name;param_name;param_value;'
		$servername = $1; #hostname
		$weblogicname = $2; #wls_name
		$param_name = $3; #param_name
		$value = $4; #param_value
		$dbh->do('INSERT INTO inv_wls (hostname, wls_name, param_name, param_value) VALUES (?, ?, ?, ?)', #add elements in the table
		undef,
		$servername, $weblogicname, $param_name, $value)  or die "can't add variables to table!";}
		elsif($e =~ /^(.*?);(.*?);(.*?);\s/s){ #search string 'hostname;wls_name;param_name;'
		$servername = $1; #hostname
		$weblogicname = $2; #wls_name
		$param_name = $3; #param_name
		$dbh->do('INSERT INTO inv_wls (hostname, wls_name, param_name) VALUES (?, ?, ?)', #add elements in the table
		undef,
		$servername, $weblogicname, $param_name) or die "can't add variables to table!";	
	}
	else{}
}
print '.';
}

close PS; #close filehandle


print 'DONE'; #print "DONE"
__END__
