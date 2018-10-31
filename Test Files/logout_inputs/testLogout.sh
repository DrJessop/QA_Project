for i in *.txt
do
	java -jar ../frontend/frontend.jar ../validservices.txt ../logout_program_outputs/transaction_summary_files/${i%.*}_tsf.log 2> /dev/null < $i > \
	../logout_program_outputs/terminal_outputs/${i%.*}_to.log 
done
