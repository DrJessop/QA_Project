for i in *.txt
do
	java -jar ../frontend/frontend.jar ../validservices.txt ../login_program_outputs/transaction_summary_files/${i%.*}_tsf.log 2> /dev/null < $i > \
	../login_program_outputs/terminal_outputs/${i%.*}_to.log 
done
