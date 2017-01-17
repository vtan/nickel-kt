#!/usr/bin/perl

use strict;
use warnings;

use utf8;
use open qw/:std :utf8/;

use JSON::PP;

my $url = 'http://localhost:8080/api/expenses';
my $header = 'Content-Type: application/json';

while (<>) {
    if (/^-/) {
        my ($date, $amount, $category, $description) =
            /^-\s+(\d{4}-\d{2}-\d{2})\s+(\d+)\s+"(.*?)"\s+"(.*?)"\s+$/;

        my %object = (
            date => $date,
            amount => int $amount,
            category => $category
        );
        my $json = encode_json \%object;
        utf8::decode $json;

        my $cmd = "curl -sS -X POST $url -d '$json' --header '$header'";
        `$cmd`;
    }
}
