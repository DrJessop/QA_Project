for i in *program_outputs
do
	for folder in "$i"/*
	do 
		echo ${folder#*/} >> "$i"/failure.txt
		for file in "$folder"/*
		do 
			if [ -f "${file}" ]
			then
				#FILE=${file%_in*}_out${file#*_in}
				#FILE=${FILE#*program_outputs/}
				#FILE=${i%_program*}_outputs/${FILE%.log*}.txt
				FILE=${i%_program*}_outputs/${folder#*/}/${file##*/}
				NUMBER=$(echo "$FILE" | grep -o -E '[0-9]+')
				FILE=${FILE%.log*}.txt
				#echo "1 $file"
				#echo "2 $i"
				#echo "3 $folder"
				#echo "4 $FILE"
				cmp $file $FILE && echo "$NUMBER" >> "$i"/failure.txt 2> /dev/null
			fi
		done
	done
done
