from flask import Flask, request, send_file
from flask_restx import Api, Resource
import mysql.connector
import json
import json
import sys
from pymongo import MongoClient
import random
import re
import numpy as np
import os
import os.path

app = Flask(__name__)
api = Api(app)


@api.route('/list/<string:level>')
class getList(Resource):
    def get(self, level):
        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        query = "SELECT UserWord FROM Manager WHERE Token = '%s'" \
            % request.headers['UserToken']
        db_userCursor.execute(query)
        rawData = db_userCursor.fetchone()[0]

        userRecord = []

        if rawData != None:
            userRecord = rawData.split(";")

        user_db.close()

        try:
            word_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_cursor = word_db.cursor()

        query = "SELECT * FROM wordTable WHERE level = %s" % level

        db_cursor.execute(query)
        records = db_cursor.fetchall()

        payload = []
        content = {}
        data = {}

        for result in records:
            temp = []
            for i in range(len(result)):
                if isinstance(result[i], bytearray):
                    # print("true")
                    temp.append(result[i].decode())
                else:
                    temp.append(result[i])

            index_w = temp[0]

            query = "SELECT index_sentence FROM linkTable WHERE index_word = %d" % index_w
            db_cursor.execute(query)
            index_sentence = db_cursor.fetchall()[0][0]

            query = "SELECT sentence, mean_s FROM sentenceTable WHERE index_s = %d" % index_sentence
            db_cursor.execute(query)
            record = db_cursor.fetchall()[0]

            sentence = record[0]
            mean_s = record[1]

            if isinstance(record[0], bytearray):
                sentence = record[0].decode()

            if isinstance(record[1], bytearray):
                mean_s = record[1].decode()

            if rawData != None and str(temp[0]) in userRecord:
                userWord = 1
            else:
                userWord = 0

            # print(userRecord)
            # print(userWord)

            content = {'index': temp[0], 'word': temp[1], 'mean_w': temp[2],
                       'part': temp[3], 'level': temp[6], 'sentence': sentence, 'mean_s': mean_s, 'userWord': userWord}

            payload.append(content)
            content = {}

        data['result'] = payload

        #print(json.dumps(data, ensure_ascii=False, indent="\t"))
        # print(type(data))

        word_db.close()

        return data


@api.route('/userWordList')
class getUserWordList(Resource):
    def get(self):
        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        query = "SELECT UserWord FROM Manager WHERE Token = '%s'" \
            % request.headers['UserToken']
        db_userCursor.execute(query)
        rawData = db_userCursor.fetchone()[0]

        userRecord = []

        if rawData != None:
            userRecord = rawData.split(";")

            try:
                word_db = mysql.connector.connect(host="",
                                                  user="", passwd="", database="", charset="utf8")
            except:
                sys.exit(
                    "Error connecting to the database. Please check your inputs.")

            db_cursor = word_db.cursor()

            query = "SELECT * FROM wordTable WHERE"

            for i in range(len(userRecord)):
                query += " index_w = " + userRecord[i] + " OR"

            fin_query = query[:-2]

            print(fin_query)

            db_cursor.execute(fin_query)
            records = db_cursor.fetchall()

            payload = []
            content = {}
            data = {}

            for result in records:
                temp = []
                for i in range(len(result)):
                    if isinstance(result[i], bytearray):
                        # print("true")
                        temp.append(result[i].decode())
                    else:
                        temp.append(result[i])

                index_w = temp[0]

                query = "SELECT index_sentence FROM linkTable WHERE index_word = %d" % index_w
                db_cursor.execute(query)
                index_sentence = db_cursor.fetchall()[0][0]

                query = "SELECT sentence, mean_s FROM sentenceTable WHERE index_s = %d" % index_sentence
                db_cursor.execute(query)
                record = db_cursor.fetchall()[0]

                sentence = record[0]
                mean_s = record[1]

                if isinstance(record[0], bytearray):
                    sentence = record[0].decode()

                if isinstance(record[1], bytearray):
                    mean_s = record[1].decode()

                if rawData != None and str(temp[0]) in userRecord:
                    userWord = 1
                else:
                    userWord = 0

                # print(userRecord)
                # print(userWord)

                content = {'index': temp[0], 'word': temp[1], 'mean_w': temp[2],
                           'part': temp[3], 'level': temp[6], 'sentence': sentence, 'mean_s': mean_s, 'userWord': userWord}

                payload.append(content)
                content = {}

            data['result'] = payload

            print(json.dumps(data, ensure_ascii=False, indent="\t"))
            print(type(data))

            user_db.close()
            word_db.close()

            return data

        user_db.close()

        return "Failed"


@api.route('/userWord/<int:index>')
class userWord(Resource):
    def put(self, index):
        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        query = "SELECT UserWord FROM Manager WHERE Token = '%s'" \
            % request.headers['UserToken']
        db_userCursor.execute(query)
        rawData = db_userCursor.fetchone()[0]

        record = []

        if rawData != None:
            record = rawData.split(";")

        if str(index) not in record:
            record.append(str(index))

        query = "UPDATE Manager SET UserWord = %s WHERE Token = '%s'" \
            % ("'" + ';'.join(record) + "'", request.headers['UserToken'])

        db_userCursor.execute(query)
        user_db.commit()

        user_db.close()

        return "Success"

    def delete(self, index):
        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        query = "SELECT UserWord FROM Manager WHERE Token = '%s'" \
            % request.headers['UserToken']
        db_userCursor.execute(query)
        rawData = db_userCursor.fetchone()[0]

        record = []

        if rawData != None:
            record = rawData.split(";")

            if str(index) in record:
                record.remove(str(index))

                if len(record) == 0:
                    query = "UPDATE Manager SET UserWord = null WHERE Token = '%s'" \
                        % (request.headers['UserToken'])

                else:
                    query = "UPDATE Manager SET UserWord = %s WHERE Token = '%s'" \
                        % ("'" + ';'.join(record) + "'", request.headers['UserToken'])

                db_userCursor.execute(query)
                user_db.commit()

                user_db.close()

            else:
                print("None!")

        return "Success"


@api.route('/problem/<string:type>/<int:level>')
class getProblem(Resource):
    def get(self, type, level):
        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        print(request.headers['UserToken'])

        client = MongoClient("")
        db = client["VocaUp"]
        collection = db[type]

        results = collection.find({"level": level})

        payload = []

        pattern = re.compile('.*set_([0-9]*).json$')

        for result in results:
            result['_id'] = str(result['_id'])
            payload.append(result)

        # print(payload[0]['meta'])
        if type != "cross_puz":
            print(type)
            # 사용자가 풀었던 문제 Set 확인 -> 상위 5개만 기억('meta' 파일)
            query = "SELECT ProblemSet FROM Manager WHERE Token = '%s'" \
                % request.headers['UserToken']
            db_userCursor.execute(query)
            rawData = db_userCursor.fetchone()[0]

            record = []

            for i in range(len(payload)):
                print(payload[i]['meta'])

            print(len(payload))
            print()

            flag = False

            removeVal = []

            if rawData != None:
                record = rawData.split(";")
                for i in range(len(payload)):
                    for j in range(len(record)):
                        if (pattern.match(payload[i]['meta'])).group(1) == record[j]:
                            flag = True
                            break

                    if flag:
                        removeVal.append(i)
                        flag = False

                arr = np.array(payload)
                leftVal = np.delete(arr, removeVal)

            else:
                leftVal = payload

            print(record)
            print()
            print(removeVal)
            print()
            print(len(leftVal))
            print()

            for i in range(len(leftVal)):
                print(leftVal[i]['meta'])

            # choice 이용하여 완성!

            target = random.choice(leftVal)

            # print(target)

            data = {}
            data = target

            print(json.dumps(data, ensure_ascii=False, indent="\t"))

            record.append(str((pattern.match(target['meta'])).group(1)))
            print(record)

            if len(record) == 6:
                record.pop(0)

            print(';'.join(record))

            query = "UPDATE Manager SET ProblemSet = %s WHERE Token = '%s'" \
                % ("'" + ';'.join(record) + "'", request.headers['UserToken'])
            print(query)
            db_userCursor.execute(query)
            user_db.commit()

        else:
            print(type)
            data = random.choice(payload)
            print(json.dumps(data, ensure_ascii=False, indent="\t"))

        user_db.close()
        client.close()

        if results == None:
            return "Failed"
        else:
            return data


@api.route('/wordPron/<int:level>/<string:word>')
class getWordPron(Resource):
    def get(self, word, level):
        target = f'./MP3/{level}/{word}'
        return send_file(target, mimetype='audio/mp3', as_attachment=True)


@api.route('/wordPic/<int:level>/<string:word>')
class getWordPic(Resource):
    def get(self, word, level):
        target = f'./IMAGE/{level}/{word}.jpg'
        if os.path.exists(target):
            return send_file(target, mimetype='image/*', as_attachment=True)
        else:
            target = f'./IMAGE/{level}/{word}.png'
            return send_file(target, mimetype='image/*', as_attachment=True)


@api.route('/user')
class userInfo(Resource):
    def post(self):
        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        data = request.get_json()

        query = "SELECT * FROM Manager WHERE Token = '%s' " % data["Token"]
        db_userCursor.execute(query)
        records = db_userCursor.fetchall()

        result = {}

        if len(records) == 0:
            print("Execute")
            query = "INSERT INTO Manager(Token) VALUES('%s')" % data["Token"]
            db_userCursor.execute(query)
            user_db.commit()

        elif len(records) == 1:
            for record in records:
                result = {"Token": record[0], "Level": record[1], "UserWord": record[2],
                          "ProblemSet": record[3], "blank_spelling": record[4], "mean_spelling": record[5],
                          "spelling_mean": record[6], "spelling_mean_link": record[7], "spelling_sort": record[8],
                          "pron_mean": record[9], "cross_puz": record[10], "recap": record[11]}
        else:
            print("Something wrong...")

        print(json.dumps(result, ensure_ascii=False, indent="\t"))

        user_db.close()

        return result

    def put(self):
        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        data = request.get_json()

        #print(json.dumps(data, ensure_ascii=False, indent="\t"))

        target = list(data.keys())
        # print(data.keys())

        query = "UPDATE Manager SET"

        for i in range(1, len(target)):
            query += " " + str(target[i]) + " = " + str(data[target[i]]) + ","

        if len(target) == 10:
            query += " ProblemSet = null,"

        fin_query = query[:-1] + " WHERE Token = '%s'" % data["Token"]

        db_userCursor.execute(fin_query)
        user_db.commit()

        user_db.close()

        return "Success"


@api.route('/wrongList/<int:level>')
class appendWrongList(Resource):
    def put(self, level):
        data = request.get_json()

        # print(json.dumps(data, ensure_ascii = False, indent = "\t"))

        list = data.get("wrongList")

        query = "SELECT index_w FROM wordTable WHERE level = %s AND (" % level

        for i in range(len(list)):
            query += "word = '" + list[i] + \
                "' OR word LIKE '" + list[i] + "_' OR "

        try:
            word_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_cursor = word_db.cursor()
        db_cursor.execute(query[:-4] + ")")
        records = db_cursor.fetchall()

        target = []

        for result in records:
            target.append(result[0])

        print(target)

        word_db.close()

        try:
            user_db = mysql.connector.connect(host="",
                                              user="", passwd="", database="member", charset="utf8")
        except:
            sys.exit("Error connecting to the database. Please check your inputs.")

        db_userCursor = user_db.cursor()

        query = "SELECT UserWord FROM Manager WHERE Token = '%s'" \
            % request.headers['UserToken']
        db_userCursor.execute(query)
        rawData = db_userCursor.fetchone()[0]

        record = []

        if rawData != None:
            record = rawData.split(";")

        for i in range(len(target)):
            if str(target[i]) not in record:
                record.append(str(target[i]))

        query = "UPDATE Manager SET UserWord = %s WHERE Token = '%s'" \
            % ("'" + ';'.join(record) + "'", request.headers['UserToken'])

        db_userCursor.execute(query)
        user_db.commit()

        user_db.close()

        return "Success"


app.run(host="0.0.0.0", port=5000)
