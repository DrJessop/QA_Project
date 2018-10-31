for i in *program_outputs
do
	echo "${i%_*}_failures" >> failure_report.txt
	echo "****" >> failure_report.txt
	cat "$i"/failure.txt >> failure_report.txt
	echo " " >> failure_report.txt
done
