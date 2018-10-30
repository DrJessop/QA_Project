for i in *.txt
do
	java -jar ../frontend/frontend.jar ../validservices.txt ../deleteservice_program_outputs/transaction_summary_files/${i%.*}_tsf.txt 2> /dev/null < $i > \
	../deleteservice_program_outputs/terminal_outputs/${i%.*}.log 
done
